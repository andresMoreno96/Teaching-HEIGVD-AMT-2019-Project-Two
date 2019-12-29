package ch.heigvd.amt.user.spec.steps;

import ch.heigvd.amt.user.api.dto.User;
import ch.heigvd.amt.user.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ch.heigvd.amt.user.ApiException;
import ch.heigvd.amt.user.ApiResponse;
import ch.heigvd.amt.user.api.DefaultApi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class CreationSteps {

    private Environment environment;
    private DefaultApi api;

    User user;

    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public CreationSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^there is a user management server$")
    public void there_is_a_User_Management_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("^I have a user information payload$")
    public void i_have_a_user_information_payload() throws Throwable {
        user = new User()
                .email("pot@ato")
                .firstName("Jack")
                .lastName("Eri")
                .password("secret");
    }

    @When("^I POST it to the /users endpoint$")
    public void i_POST_it_to_the_users_endpoint() throws Throwable {
        try {
            lastApiResponse = api.createUserWithHttpInfo(user);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }

    }

    @Then("^I receive a (\\d+) status code$")
    public void i_receive_a_status_code(int arg1) throws Throwable {
        assertEquals(200, lastStatusCode);
    }

}
