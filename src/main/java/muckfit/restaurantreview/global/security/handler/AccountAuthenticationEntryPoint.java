package muckfit.restaurantreview.global.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import muckfit.restaurantreview.global.exception.ExceptionCode;
import muckfit.restaurantreview.global.exception.dto.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class AccountAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ExceptionCode unAuthenticationExCode = ExceptionCode.UN_AUTHENTICATION;
        ErrorResponse unAuthException =
                new ErrorResponse("UnAuthentication",
                        ExceptionCode.UN_AUTHENTICATION.getMessage(), unAuthenticationExCode.getCode());

        String authenticationExJson = new Gson().toJson(unAuthException);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(authenticationExJson);
    }
}
