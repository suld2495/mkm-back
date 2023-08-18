package kr.co.mkm.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import kr.co.mkm.login.service.User;
import kr.co.mkm.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private String secretKey;
    private UserMapper userMapper;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserMapper userMapper, String key) {
        super(authenticationManager);
        this.userMapper = userMapper;
        this.secretKey = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String URI = request.getRequestURI();
        if (URI.equals("/api/admin/refresh") || URI.equals("/api/admin/logout") || URI.equals("/api/admin/login") || !(URI.indexOf("/api/admin/") == 0)) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = request.getHeader("AccessToken");

        if ("".equals(accessToken)
                || accessToken == null
                || "Bearer null".equals(accessToken)) {
            return;
        }

        if (Pattern.matches("^Bearer .*", accessToken)) {
            accessToken = accessToken.replaceAll("^Bearer( )*", "");

            try {
                JWT.require(Algorithm.HMAC256(this.secretKey))
                        .build()
                        .verify(accessToken);
            } catch(TokenExpiredException e) {
                return;
            }

            chain.doFilter(request, response);
        }
    }
}
