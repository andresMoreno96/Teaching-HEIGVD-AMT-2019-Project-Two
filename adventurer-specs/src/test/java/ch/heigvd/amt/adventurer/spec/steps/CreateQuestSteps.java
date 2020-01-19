package ch.heigvd.amt.adventurer.spec.steps;

import ch.heigvd.amt.adventurer.ApiException;
import ch.heigvd.amt.adventurer.api.dto.Adventurer;
import ch.heigvd.amt.adventurer.api.dto.Quest;
import ch.heigvd.amt.adventurer.api.dto.QuestCreate;
import ch.heigvd.amt.adventurer.api.dto.QuestTest;
import ch.heigvd.amt.adventurer.spec.helpers.Environment;
import ch.heigvd.amt.adventurer.spec.helpers.JwtManager;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class CreateQuestSteps {

    private Environment env;
    private JwtManager jwtManager;


    public CreateQuestSteps(Environment env, JwtManager jwtManager) {
        this.env = env;
        this.jwtManager = jwtManager;
    }

    @Given("^there is a Quest management server$")
    public void thereIsAQuestManagementServer() throws IOException {
        assertNotNull(Environment.getApi());
    }


    @And("^wants to create the quest \"([^\"]*)\" with the objective of \"([^\"]*)\"$")
    public void wantsToCreateTheQuestWithTheObjectiveOf(String title, String description) throws Throwable {

        env.quest=new Quest().adventurerName(env.adventurer.getName()).title(title).description(description);


    }

    @When("^I POST it into the /quests endpoint$")
    public void iPOSTItIntoTheQuestsEndpoint() throws IOException {
        try {
            Environment.getApi().getApiClient().setApiKey("Bearer " + env.token);
            env.apiResponse = Environment.getApi().createQuestWithHttpInfo(
                    new QuestCreate().adventurerName(env.quest.getAdventurerName()).title(env.quest.getTitle())
                    .description(env.quest.getDescription()
            ));
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


    @And("^I can retrieve the Quest information$")
    public void iCanRetrieveTheQuestInformation() throws Throwable {
        Quest expected = (Quest) env.apiResponse.getData();
        Quest quest=fetchQuest(expected.getId());
        Assert.assertNotNull(quest);
        Assert.assertEquals(expected.getId(), quest.getId());
        Assert.assertEquals(expected.getTitle(), quest.getTitle());
        Assert.assertEquals(expected.getDescription(), quest.getDescription());


    }

    private Quest fetchQuest(long id) throws Throwable {
        try {
            return Environment.getApi().getQuest((int)id);
        } catch(ApiException e) {
            return null;
        }
    }

    @Given("^the adventurer$")
    public void theAdventurer() throws IOException {

        env.adventurer=new Adventurer().name("popo").job("robber");

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
}
