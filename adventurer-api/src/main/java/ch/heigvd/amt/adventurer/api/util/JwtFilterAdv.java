package ch.heigvd.amt.adventurer.api.util;

import ch.heigvd.amt.adventurer.services.JwtManagerAdv;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtFilterAdv implements Filter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_PREFIX = "Bearer ";

    public static final String EMAIL_REQUEST_ATTRIBUTE = "Email";
    public static final String PWD_RESET_REQUEST_ATTRIBUTE = "PasswordReset";

    @Autowired
    private JwtManagerAdv jwtManager;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader != null && authHeader.startsWith(AUTH_PREFIX)) {
            String token = authHeader.substring(AUTH_PREFIX.length());

            DecodedJWT decodedJWT = jwtManager.decodeToken(token);
            if (decodedJWT != null && jwtManager.checkExpiration(decodedJWT)) {

                String email = jwtManager.getUserEmail(decodedJWT);
                if (email != null) {
                    request.setAttribute(EMAIL_REQUEST_ATTRIBUTE, email);
                }

                long pwdReset = jwtManager.getPasswordReset(decodedJWT);
                if (pwdReset >= 0) {
                    request.setAttribute(PWD_RESET_REQUEST_ATTRIBUTE, pwdReset);
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

