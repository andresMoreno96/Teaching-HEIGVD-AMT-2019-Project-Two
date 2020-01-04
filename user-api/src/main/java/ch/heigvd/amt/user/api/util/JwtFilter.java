package ch.heigvd.amt.user.api.util;

import ch.heigvd.amt.user.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter implements Filter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_PREFIX = "Bearer ";

    private JwtManager jwtManager;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public void init(FilterConfig filterConfig) {
        String secret = filterConfig.getInitParameter("jwtSecret");
        jwtManager = new JwtManager(secret);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader(AUTH_HEADER);

        System.out.println(authHeader);

        if (authHeader != null && authHeader.startsWith(AUTH_PREFIX)) {
            String email = jwtManager.verifyToken(authHeader.substring(AUTH_PREFIX.length()));

            if (usersRepository.existsById(email)) {

                filterChain.doFilter(request, response);
            }
        }

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
