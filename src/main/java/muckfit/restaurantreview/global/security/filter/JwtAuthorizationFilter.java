package muckfit.restaurantreview.global.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import muckfit.restaurantreview.global.security.authentication.Principal;
import muckfit.restaurantreview.global.security.jwt.JwtProcessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtProcessor jwtProcessor;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProcessor jwtProcessor) {
        super(authenticationManager);
        this.jwtProcessor = jwtProcessor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String jwtHeader = request.getHeader(jwtProcessor.getHeader());

        // verifyJwtHeader
        if (jwtProcessor.isCorrectJwtToken(jwtHeader)) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = jwtProcessor.extractJwtToken(jwtHeader);
        Claims claims = jwtProcessor.verifyJwtToken(jwtToken);

        Long accountId = Long.valueOf(claims.getSubject());
        String accountEmail = (String) claims.get("email");
        List<String> accountRoles = (List<String>) claims.get("role");
        List<SimpleGrantedAuthority> authorities = accountRoles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        Principal principal = new Principal(accountId, accountEmail);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
