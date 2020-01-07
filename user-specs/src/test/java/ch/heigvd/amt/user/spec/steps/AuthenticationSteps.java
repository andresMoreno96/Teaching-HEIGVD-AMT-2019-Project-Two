package ch.heigvd.amt.user.spec.steps;

import ch.heigvd.amt.user.ApiException;
import ch.heigvd.amt.user.spec.helpers.Environment;
import ch.heigvd.amt.user.spec.helpers.JwtManager;
import com.auth0.jwt.interfaces.DecodedJWT;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AuthenticationSteps {

    private Environment env;
    private JwtManager jwtManager;

    public AuthenticationSteps(Environment env, JwtManager jwtManager) {
        this.env = env;
        this.jwtManager = jwtManager;
    }

    @When("^I authenticate at /authentication$")
    public void iAuthenticateAtAuthentication() throws Throwable {
        try {
            env.apiResponse = Environment.getApi()
                    .authenticateUserWithHttpInfo(env.user.getEmail(), env.user.getPassword());
            env.apiCallThrewException = false;
            env.apiException = null;
            env.statusCode = env.apiResponse.getStatusCode();
        } catch (ApiException e) {
            env.apiCallThrewException = true;
            env.apiResponse = null;
            env.apiException = e;
            env.statusCode = env.apiException.getCode();
        }
    }

    @Then("^I receive an auth jwt for (.*)$")
    public void iReceiveAnAuthJwtFor(String email) throws Throwable {
        DecodedJWT decodedJWT = jwtManager.decodeToken((String) env.apiResponse.getData());

        assertEquals(decodedJWT.getClaims().size(), 2);
        assertNotNull(decodedJWT.getClaim(JwtManager.USER_EMAIL));
        assertNotNull(decodedJWT.getExpiresAt());

        assertEquals(email, jwtManager.getUserEmail(decodedJWT));
    }
}
