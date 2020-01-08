package ch.heigvd.amt.user.spec.steps;

import ch.heigvd.amt.user.ApiException;
import ch.heigvd.amt.user.api.dto.UserNoPassword;
import ch.heigvd.amt.user.spec.helpers.Environment;
import ch.heigvd.amt.user.spec.helpers.JwtManager;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.Env;
import org.junit.Assert;

public class GetUserSteps {

    private Environment env;
    private JwtManager jwtManager;
    private String token;

    public GetUserSteps(Environment env, JwtManager jwtManager) {
        this.env = env;
        this.jwtManager = jwtManager;
    }

    @Given("^a user jwt for (.*)$")
    public void aUserJwtFor(String email) {
        token = jwtManager.createToken(email);
    }

    @Given("^an invalid jwt$")
    public void anInvalidJwt() {
        token = JWT.create().sign(Algorithm.HMAC256("wrong.secret"));
    }

    @When("^I GET to /users/(.*)$")
    public void iGETToUsers(String email) throws Throwable {
        try {
            Environment.getApi().getApiClient().setApiKey("Bearer " + token);
            env.apiResponse = Environment.getApi().getUserWithHttpInfo(email);
            env.apiCallThrewException = false;
            env.apiException = null;
            env.statusCode = env.apiResponse.getStatusCode();
        } catch (ApiException e) {
            env.apiCallThrewException = true;
            env.apiResponse = null;
            env.apiException = e;
            env.statusCode = env.apiException.getCode();
        } finally {
            Environment.getApi().getApiClient().setApiKey(null);
        }
    }

    @Then("^I got the user information (.*) (.*) (.*)$")
    public void iGotTheUserInformationGetPotAtoPotAto(String email, String firstName, String lastName) {
        UserNoPassword user = (UserNoPassword) env.apiResponse.getData();

        Assert.assertNotNull(user);
        Assert.assertEquals(email, user.getEmail());
        Assert.assertEquals(firstName, user.getFirstName());
        Assert.assertEquals(lastName, user.getLastName());
    }
}
