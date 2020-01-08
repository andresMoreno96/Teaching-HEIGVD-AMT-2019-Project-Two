package ch.heigvd.amt.user.spec.steps;

import ch.heigvd.amt.user.ApiException;
import ch.heigvd.amt.user.spec.helpers.Environment;
import ch.heigvd.amt.user.spec.helpers.JwtManager;
import com.auth0.jwt.interfaces.DecodedJWT;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.activation.DataHandler;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResetPasswordSteps {

    private Environment env;
    private JwtManager jwtManager;
    private Wiser wiser;
    private int emailCount;
    private String token;

    public ResetPasswordSteps(Environment env, JwtManager jwtManager) {
        this.env = env;
        this.jwtManager = jwtManager;
        this.wiser = new Wiser();
    }

    @When("^I POST to /users/(.*)/password-reset$")
    public void iPostToUsersPasswordReset(String email) throws Throwable {

        wiser.setHostname("localhost");
        wiser.setPort(2525);
        wiser.start();
        emailCount = wiser.getMessages().size();

        try {
            env.apiResponse = Environment.getApi().resetPasswordWithHttpInfo(email);
            env.apiCallThrewException = false;
            env.apiException = null;
            env.statusCode = env.apiResponse.getStatusCode();
        } catch (ApiException e) {
            env.apiCallThrewException = true;
            env.apiResponse = null;
            env.apiException = e;
            env.statusCode = env.apiException.getCode();
        }

        wiser.stop();
    }

    @Then("^I receive an email for (.*) with a jwt$")
    public void iReceiveAnEmailForWithAJwt(String email) throws Throwable {
        Assert.assertEquals(emailCount + 1, wiser.getMessages().size());

        WiserMessage mail = wiser.getMessages().get(emailCount);
        Assert.assertEquals(email, mail.getEnvelopeReceiver());

        DataHandler dataHandler = mail.getMimeMessage().getDataHandler();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        dataHandler.writeTo(baos);
        baos.flush();
        token = baos.toString();
    }


    @Then("^the jwt is for a password reset for (.*)$")
    public void iReceiveAPasswordResetJwtFor(String email) throws Throwable {
        DecodedJWT decodedJWT = jwtManager.decodeToken(token);

        assertEquals(decodedJWT.getClaims().size(), 3);
        assertNotNull(decodedJWT.getClaim(JwtManager.PWD_RESET));
        assertNotNull(decodedJWT.getClaim(JwtManager.USER_EMAIL));
        assertNotNull(decodedJWT.getExpiresAt());

        assertEquals(email, jwtManager.getUserEmail(decodedJWT));
    }

    @And("^no email si sent$")
    public void noEmailSiSent() {
        assertEquals(emailCount, wiser.getMessages().size());
    }
}
