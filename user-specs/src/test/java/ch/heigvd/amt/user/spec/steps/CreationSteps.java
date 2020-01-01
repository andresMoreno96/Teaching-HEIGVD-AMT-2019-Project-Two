package ch.heigvd.amt.user.spec.steps;

import ch.heigvd.amt.user.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ch.heigvd.amt.user.ApiException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class CreationSteps {

    private Environment env;

    public CreationSteps(Environment environment) {
        this.env = environment;
    }

    @Given("^there is a user management server$")
    public void there_is_a_User_Management_server() throws Throwable {
        assertNotNull(Environment.getApi());
    }

    @When("^I POST it to the /users endpoint$")
    public void i_POST_it_to_the_users_endpoint() throws Throwable {
        try {
            env.apiResponse = Environment.getApi().createUserWithHttpInfo(env.user);
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

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int statusCode) throws Throwable {
        assertEquals(statusCode, env.statusCode);
    }
}
