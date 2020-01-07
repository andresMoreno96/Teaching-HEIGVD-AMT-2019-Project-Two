package ch.heigvd.amt.user.spec.steps;

import ch.heigvd.amt.user.ApiException;
import ch.heigvd.amt.user.spec.helpers.Environment;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.activation.DataHandler;
import java.io.ByteArrayOutputStream;

public class PasswordResetRequestSteps {

    private Environment env;
    private Wiser wiser;
    private int emailCount;
    private String token;

    public PasswordResetRequestSteps(Environment env) {
        this.env = env;
        this.wiser = new Wiser();
    }

    @When("^I POST to /users/(.*)/password-reset$")
    public void iPostToUsersPasswordReset(String email) throws Throwable {

        wiser.setHostname("localhost");
        wiser.setPort(3030);
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

    @And("^the jwt is for a password reset for the user (.*)$")
    public void theJwtIsForAPasswordResetForTheUserRequestPotAto(String email) throws Throwable {
        //TODO: check token
        System.out.println(token);
    }
}
