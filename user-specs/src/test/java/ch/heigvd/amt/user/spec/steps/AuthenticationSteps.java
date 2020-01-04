package ch.heigvd.amt.user.spec.steps;

import ch.heigvd.amt.user.ApiException;
import ch.heigvd.amt.user.spec.helpers.Environment;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertNotNull;

public class AuthenticationSteps {

    private Environment env;

    public AuthenticationSteps(Environment environment) {
        this.env = environment;
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

    @Then("^I receive a jwt with the email pot@ato$")
    public void iReceiveAJwtWithTheEmailPotAto() throws Throwable {
        assertNotNull(env.apiResponse.getData());
    }
}