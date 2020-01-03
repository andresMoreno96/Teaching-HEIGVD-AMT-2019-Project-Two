package ch.heigvd.amt.user.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

public class JwtManager {

    private static final String PWD_RESET_ID = "passwordReset";
    private static final String PWD_RESET_EMAIL = "email";
    private static final String USER_EMAIL = "user";

    private String secret;
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public JwtManager(String secret) {
        this.secret = secret;

        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
    }

    public String passwordResetToken(String id, String email, Date expireOn) {
        return JWT.create()
                .withClaim("passwordReset", id)
                .withClaim("email", email)
                .withExpiresAt(expireOn)
                .sign(algorithm);
    }

    public boolean verifyPasswordResetToken(String token, String id, String email) {

        Date now = new Date();
        DecodedJWT decodedJWT = verifier.verify(token);

        if (decodedJWT.getExpiresAt().before(now)) {
            return false;
        }

        Map<String, Claim> claims = decodedJWT.getClaims();

        return claims.containsKey(PWD_RESET_ID) && claims.containsKey(PWD_RESET_EMAIL)
                && id.equals(claims.get(PWD_RESET_ID).asString())
                && email.equals(claims.get(PWD_RESET_EMAIL).asString());
    }

    public String createToken(String email) {
        return JWT.create().withClaim(USER_EMAIL, email).sign(algorithm);
    }

    public String verifyToken(String token) {
        DecodedJWT decoded = verifier.verify(token);
        Claim emailClaim = decoded.getClaim(USER_EMAIL);

        return emailClaim.asString();
    }
}
