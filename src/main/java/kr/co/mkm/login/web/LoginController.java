package kr.co.mkm.login.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import kr.co.mkm.login.service.LoginService;
import kr.co.mkm.login.service.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.regex.Pattern;


@RestController
@PropertySource("classpath:static/properties/globals.properties")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Value("${host.key}")
    private String secretKey;
    private String createToken(String userPk) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(userPk);
            Date now = new Date();
            int tokenValidMillisecond = 2 * 60 * 60 * 1000;
            String token = JWT.create()
                    .withIssuer("admin")
                    .withIssuedAt(now)
                    .withExpiresAt(new Date(now.getTime() + tokenValidMillisecond))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            return "";
        }
    }

    private String createRefreshToken(String userPk, String access) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(userPk);
            Date now = new Date();
            int tokenValidMillisecond = 10 * 24 * 60 * 60 * 1000;
            String token = JWT.create()
                    .withIssuedAt(now)
                    .withExpiresAt(new Date(now.getTime() + tokenValidMillisecond))
                    .withClaim("AccessToken", access)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            return "";
        }
    }

    @PostMapping("/api/admin/login")
    public ResponseEntity login(@RequestBody User user, HttpServletResponse response) throws Exception {
        if (user.getId() == null || user.getId().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        if (!user.getId().equals("admin") || !user.getPassword().equals("admin")) {
            return ResponseEntity.status(401).build();
        }

        String token = createToken(secretKey);
        String refreshToken = createRefreshToken(secretKey, token);
        User loginedUser = new User();
        loginedUser.setId(user.getId());

        response.setHeader("token", token);
        response.setHeader("refresh", refreshToken);

        loginService.insertRefreshToken(refreshToken);
        return ResponseEntity.status(200).body(loginedUser);
    }

    @PostMapping("/api/admin/refresh")
    public ResponseEntity refresh(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = request.getHeader("AccessToken");
        String refreshToken = request.getHeader("refreshToken");

        String storedRefreshToken = loginService.getRefreshToken();

        if ("".equals(refreshToken)
                || "Bearer null".equals(refreshToken)
                || refreshToken == null) {
            return ResponseEntity.status(403).build();
        }

        if (Pattern.matches("^Bearer .*", refreshToken)) {
            refreshToken = refreshToken.replaceAll("^Bearer( )*", "");
            String restoreAccessToken = "";
            try {
                if (!refreshToken.equals(storedRefreshToken)) {
                    throw new Exception();
                }

                restoreAccessToken = JWT.require(Algorithm.HMAC256(this.secretKey))
                        .build()
                        .verify(refreshToken)
                        .getClaim("AccessToken").asString();
            } catch(TokenExpiredException e) {
                if (!restoreAccessToken.equals(accessToken)) {
                    return ResponseEntity.status(403).build();
                }
            } catch (Exception e) {
                return ResponseEntity.status(403).build();
            }
        } else {
            return ResponseEntity.status(403).build();
        }

        String token = createToken(secretKey);
        response.setHeader("token", token);
        User loginedUser = new User();
        loginedUser.setId("admin");

        return ResponseEntity.status(200).body(loginedUser);
    }

    @DeleteMapping("/api/admin/logout")
    public ResponseEntity logout() {
        loginService.deleteRefreshToken();
        return ResponseEntity.status(200).build();
    }
}
