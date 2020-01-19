package ch.heigvd.amt.adventurer.spec.steps;

import ch.heigvd.amt.adventurer.ApiException;
import ch.heigvd.amt.adventurer.api.dto.Adventurer;
import ch.heigvd.amt.adventurer.spec.helpers.Environment;
import ch.heigvd.amt.adventurer.spec.helpers.JwtManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateAdventurerSteps {

    private Environment env;
    private JwtManager jwtManager;

    public CreateAdventurerSteps(Environment env, JwtManager jwtManager) {
        this.env = env;
        this.jwtManager = jwtManager;

    }


    @Given("^there is an adventurer management server$")
    public void thereIsAnAdventurerManagementServer() throws IOException {
        assertNotNull(Environment.getApi());
    }

    @Given("^a user jwt for \"([^\"]*)\"$")
    public void aUserJwtFor(String email) throws Throwable {
        env.token= jwtManager.createToken(email);

    }

    @And("^the adventurer \"([^\"]*)\" is a \"([^\"]*)\"$")
    public void theAdventurerIsA(String name, String job) throws Throwable {

        env.adventurer=new Adventurer().name(name).job(job);
    }

    @When("^I POST it into the /adventurers endpoint$")
    public void iPOSTItIntoTheAdventurersEndpoint() throws IOException {

        try {
            Environment.getApi().getApiClient().setApiKey("Bearer " + env.token);
            env.apiResponse = Environment.getApi().createAdventurerWithHttpInfo(env.adventurer);
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




    @And("^the adventurer still has the job \"([^\"]*)\"$")
    public void theAdventurerStillHasTheJob(String job) throws Throwable {

        Adventurer adventurer= fetchAdventurer(env.adventurer.getName());

        Assert.assertNotNull(adventurer);
        Assert.assertEquals(job, adventurer.getJob());

    }

    @Given("^a malformed adventurer$")
    public void aMalformedAdventurer() {
        env.adventurer= new Adventurer().job("worker");
        
    }

    @And("^the adventurer does not exist$")
    public void theAdventurerDoesNotExist() {

    }

    @Then("^I receive a (\\d+) status code$")
    public void iReceiveAStatusCode(int statusCode) {
        assertEquals(statusCode, env.statusCode);
    }

    @And("^I can retrieve the adventurer information$")
    public void iCanRetrieveTheAdventurerInformation() throws Throwable {
        Adventurer adventurer=fetchAdventurer(env.adventurer.getName());

        Assert.assertNotNull(adventurer);
        Assert.assertEquals(adventurer.getName(), env.adventurer.getName());
        Assert.assertEquals(adventurer.getJob(), env.adventurer.getJob());

    }

    @Given("^there is an adventurer with the named \"([^\"]*)\" and he is a \"([^\"]*)\"$")
    public void thereIsAnAdventurerWithTheNamedAndHeIsA(String name, String job) throws Throwable {

        Adventurer adventurer=fetchAdventurer(name);

        if(adventurer!=null){
            Assert.assertEquals(adventurer.getName(), name);
            Assert.assertEquals(adventurer.getJob(), job);
        }else {

            Environment.getApi().createAdventurer(new Adventurer().job(job).name(name));

        }

    }

    private Adventurer fetchAdventurer(String name) throws Throwable {
        try {
            return Environment.getApi().getAdventurer(name);
        } catch(ApiException e) {
            return null;
        }
    }


    @Given("^An invalid token$")
    public void anInvalidToken() {
        env.token="empty";
    }
}
