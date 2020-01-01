package ch.heigvd.amt.user.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtManager {

    private String secret;
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public JwtManager(String secret) {
        this.secret = secret;

        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
    }

    public String createToken(String payload) {
        return JWT.create().withSubject(payload).sign(algorithm);
    }

    public String verifyToken(String token) {
        return verifier.verify(token).getSubject();
    }
}
