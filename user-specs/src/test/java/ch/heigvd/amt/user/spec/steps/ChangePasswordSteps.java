package ch.heigvd.amt.user.spec.steps;

import ch.heigvd.amt.user.ApiException;
import ch.heigvd.amt.user.spec.helpers.Environment;
import ch.heigvd.amt.user.spec.helpers.JwtManager;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cucumber.api.java.ca.I;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.Env;
import org.junit.Assert;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.activation.DataHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ChangePasswordSteps {

    private Environment env;
    private JwtManager jwtManager;
    private Wiser wiser;
    private String token;

    public ChangePasswordSteps(Environment env, JwtManager jwtManager) {
        this.env = env;
        this.jwtManager = jwtManager;
        wiser = new Wiser();
        wiser.setHostname("localhost");
        wiser.setPort(2525);
    }

    @Given("^a password reset token for (.*)$")
    public void aPasswordResetTokenFor(String email) throws Throwable {
        try {
            wiser.start();

            Environment.getApi().resetPassword(email);

            if (wiser.getMessages().isEmpty()) { return; }
            WiserMessage mail = wiser.getMessages().get(wiser.getMessages().size() - 1);

            DataHandler dataHandler = mail.getMimeMessage().getDataHandler();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            dataHandler.writeTo(baos);
            baos.flush();
            token = baos.toString()
                    .replace("\r", "")
                    .replace("\n", "");

        } catch (ApiException e) {
            Assert.fail("no token for " + email);
        } finally {
            wiser.stop();
        }
    }

    @Given("^a user token for (.*) (.*)")
    public void aUserTokenFor(String email, String password) throws Throwable {
        String token = authenticate(email, password);
        if (token != null) {
            this.token = token;
        } else {
            Assert.fail(String.format("could not get user token for %s %s", email, password));
        }
    }

    @When("^I POST to /passwords with the new password (.*)$")
    public void iPOSTToPasswordsWithTheNewPassword(String password) throws Throwable {
        changePassword(password, token);
    }

    @When("^I POST to /passwords without token with the new password (.*)$")
    public void iPOSTToPasswordsWithoutTokenWithTheNewPassword(String password) throws Throwable {
        changePassword(password, null);
    }

    @Then("^I can authenticate with the credentials (.*) (.*)$")
    public void iCanAuthenticateWithTheCredentials(String email, String password) throws Throwable {
        if (authenticate(email, password) == null) {
            Assert.fail(String.format("should be able to authenticate with %s %s", email, password));
        }
    }

    @Then("^I cannot authenticate with the credentials (.*) (.*)$")
    public void iCannotAuthenticateWithTheCredentialsChangePotAtoSecret(String email, String password) throws Throwable {
        if (authenticate(email, password) != null) {
            Assert.fail(String.format("should not be able to authenticate with %s %s", email, password));
        }
    }

    private void changePassword(String password, String token) throws IOException {
        try {
            if (token != null) {
                Environment.getApi().getApiClient().setApiKey("Bearer " + token);
            }
            env.apiResponse = Environment.getApi().changePasswordWithHttpInfo(password);
            env.apiCallThrewException = false;
            env.apiException = null;
            env.statusCode = env.apiResponse.getStatusCode();
        } catch (ApiException e) {
            env.apiCallThrewException = true;
            env.apiResponse = null;
            env.apiException = e;
            env.statusCode = env.apiException.getCode();
        } finally {
            if (token != null) {
                Environment.getApi().getApiClient().setApiKey(null);
            }
        }
    }

    private String authenticate(String email, String password) throws IOException {
        try {
            return Environment.getApi().authenticateUser(email, password);
        } catch (ApiException e) {
            return null;
        }
    }
}
