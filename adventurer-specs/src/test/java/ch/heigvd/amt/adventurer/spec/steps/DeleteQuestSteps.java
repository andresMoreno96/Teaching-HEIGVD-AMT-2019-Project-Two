package ch.heigvd.amt.adventurer.spec.steps;

import ch.heigvd.amt.adventurer.ApiException;
import ch.heigvd.amt.adventurer.api.dto.Adventurer;
import ch.heigvd.amt.adventurer.api.dto.Quest;
import ch.heigvd.amt.adventurer.api.dto.QuestCreate;
import ch.heigvd.amt.adventurer.spec.helpers.Environment;
import ch.heigvd.amt.adventurer.spec.helpers.JwtManager;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

public class DeleteQuestSteps {

    private Environment env;
    private JwtManager jwtManager;


    public DeleteQuestSteps(Environment env, JwtManager jwtManager) {
        this.env = env;
        this.jwtManager = jwtManager;
    }


    @When("^I DELETE  into the /quests endpoint$")
    public void iDELETEIntoTheQuestsEndpoint() throws IOException {

        try {
            Environment.getApi().getApiClient().setApiKey("Bearer " + env.token);
            env.apiResponse = Environment.getApi()
                    .deleteQuestWithHttpInfo((int)(long)((Quest) env.apiResponse.getData()).getId());
            System.out.println("+++++++++++++++++"+env.apiResponse.getStatusCode());
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

    @And("^the already existing quest \"([^\"]*)\" with the objective of \"([^\"]*)\"$")
    public void theAlreadyExistingQuestWithTheObjectiveOf(String title, String description) throws Throwable {
        try {
            Environment.getApi().getApiClient().setApiKey("Bearer " + env.token);
            env.quest = Environment.getApi().createQuest(new QuestCreate()
                            .adventurerName(env.adventurer.getName())
                            .title(title)
                            .description(description));

        } catch (ApiException ignore) {
            Assert.fail("no quest");
        } finally {
            Environment.getApi().getApiClient().setApiKey(null);
        }
    }
}
