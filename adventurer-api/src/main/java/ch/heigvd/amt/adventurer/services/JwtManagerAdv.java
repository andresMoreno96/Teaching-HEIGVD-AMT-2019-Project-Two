package ch.heigvd.amt.adventurer.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class JwtManagerAdv {

    private static final String PWD_RESET = "passwordReset";
    private static final String USER_EMAIL = "email";

    private Algorithm algorithm;
    private JWTVerifier verifier;

    public JwtManagerAdv(@Value("${app.jwt.secret}") String secret) {
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
    }

    public String createToken(String email) {
        return JWT.create()
                .withClaim(USER_EMAIL, email)
                .withExpiresAt(calculateExpiration(24 * 7 * 2))
                .sign(algorithm);
    }

    public String createToken(String email, long pwdReset) {
        return JWT.create()
                .withClaim(USER_EMAIL, email)
                .withClaim(PWD_RESET, pwdReset)
                .withExpiresAt(calculateExpiration(2))
                .sign(algorithm);
    }

    public DecodedJWT decodeToken(String token) {
        try {
            return verifier.verify(token);
        } catch (SignatureVerificationException | JWTDecodeException e) {
            return null;
        }
    }

    public String getUserEmail(DecodedJWT decodedJWT) {
        Claim claim = decodedJWT.getClaim(USER_EMAIL);
        return !claim.isNull() ? claim.asString() : null;
    }

    public long getPasswordReset(DecodedJWT decodedJWT) {
        Claim claim = decodedJWT.getClaim(PWD_RESET);
        return !claim.isNull() ? claim.asLong() : -1;
    }

    public boolean checkExpiration(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt == null || expiresAt.after(new Date());
    }

    private Date calculateExpiration(int hours) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.HOUR, hours);

        return calendar.getTime();
    }
}
