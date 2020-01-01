package ch.heigvd.amt.user.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Map;

public class JwtManager {

    private String secret;
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public JwtManager(String secret) {
        this.secret = secret;

        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
    }

    public String createToken(String email) {
        return JWT.create().withClaim("email", email).sign(algorithm);
    }

    public String verifyToken(String token) {
        DecodedJWT decoded = verifier.verify(token);
        Claim emailClaim = decoded.getClaim("email");

        return emailClaim.asString();
    }
}
