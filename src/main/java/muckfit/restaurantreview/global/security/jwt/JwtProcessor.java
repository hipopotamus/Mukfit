package muckfit.restaurantreview.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import muckfit.restaurantreview.global.security.authentication.UserAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtProcessor {

    @Value("${jwt.secret-Key}")
    String secretKey;

    @Value("${jwt.expiration}")
    long expiration;

    @Value("${jwt.prefix}")
    String prefix;

    @Value("${jwt.header}")
    String header;

    private Key signingKey;

    @PostConstruct
    public void keyInit() {
        this.signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createAuthJwtToken(UserAccount userAccount) {

        Map<String, Object> claims = createClaims(userAccount);

        long nowTime = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userAccount.getAccount().getId().toString())
                .setIssuedAt(new Date(nowTime))
                .setExpiration(new Date(nowTime + expiration))
                .signWith(signingKey)
                .compact();
    }

    private Map<String, Object> createClaims(UserAccount userAccount) {

        Map<String, Object> claims = new HashMap<>();

        List<String> accountRoles = userAccount.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        claims.put("email", userAccount.getUsername());
        claims.put("role", accountRoles);

        return claims;
    }

    public Claims verifyJwtToken(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public boolean isCorrectJwtToken(String jwt) {
        return jwt != null && jwt.startsWith(prefix);
    }

    public String extractJwtToken(String jwtHeader) {
        return jwtHeader.substring(prefix.length() + 1);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getHeader() {
        return header;
    }
}
