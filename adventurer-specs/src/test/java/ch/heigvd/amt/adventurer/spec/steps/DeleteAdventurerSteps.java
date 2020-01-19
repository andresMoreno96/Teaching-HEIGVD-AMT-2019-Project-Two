package ch.heigvd.amt.adventurer.spec.steps;

import ch.heigvd.amt.adventurer.ApiException;
import ch.heigvd.amt.adventurer.spec.helpers.Environment;
import ch.heigvd.amt.adventurer.spec.helpers.JwtManager;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import java.io.IOException;

public class DeleteAdventurerSteps {

    private Environment env;
    private JwtManager jwtManager;


    public DeleteAdventurerSteps(Environment env, JwtManager jwtManager) {
        this.env = env;
        this.jwtManager = jwtManager;
    }


    @When("^I DELETE it into the /adventurers endpoint$")
    public void iDELETEItIntoTheAdventurersEndpoint() throws IOException {
        try {
            Environment.getApi().getApiClient().setApiKey("Bearer " + env.token);
            env.apiResponse = Environment.getApi().deleteAdventurerWithHttpInfo(env.adventurer.getName());
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

}