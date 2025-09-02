package muckfit.restaurantreview.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import muckfit.restaurantreview.global.exception.ExceptionCode;
import muckfit.restaurantreview.global.exception.dto.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccountAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ExceptionCode unAuthenticationExCode = ExceptionCode.UN_AUTHENTICATION;
        ErrorResponse unAuthException =
                new ErrorResponse("UnAuthentication",
                        ExceptionCode.UN_AUTHENTICATION.getMessage(), unAuthenticationExCode.getCode());

        String authenticationExJson = objectMapper.writeValueAsString(unAuthException);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(authenticationExJson);
    }
}
