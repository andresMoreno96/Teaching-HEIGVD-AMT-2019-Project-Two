package ch.heigvd.amt.user.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

public class JwtManager {

    private static JwtManager instance = null;

    private static final String JWT_SECRET = "secret";
    private static final String PWD_RESET = "passwordReset";
    private static final String USER_EMAIL = "email";

    private Algorithm algorithm;
    private JWTVerifier verifier;

    private JwtManager(String secret) {
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
    }

    public static JwtManager getInstance() {

        if (instance == null) {
            instance = new JwtManager(JWT_SECRET);
        }

        return instance;
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
        return verifier.verify(token);
    }

    public String getUserEmail(DecodedJWT decodedJWT) {
        Claim claim = decodedJWT.getClaim(USER_EMAIL);
        return claim != null ? claim.asString() : null;
    }

    public long getPasswordReset(DecodedJWT decodedJWT) {
        Claim claim = decodedJWT.getClaim(PWD_RESET);
        return claim != null ? claim.asLong() : -1;
    }

    public boolean checkExpiration(DecodedJWT decodedJWT) {
        return decodedJWT.getExpiresAt().after(new Date());
    }

    private Date calculateExpiration(int hours) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        calendar.add(Calendar.HOUR, hours);

        return calendar.getTime();
    }
}
