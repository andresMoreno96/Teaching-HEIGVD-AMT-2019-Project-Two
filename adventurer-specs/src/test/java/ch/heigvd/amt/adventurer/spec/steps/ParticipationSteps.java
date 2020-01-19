package ch.heigvd.amt.adventurer.spec.steps;

import ch.heigvd.amt.adventurer.ApiException;
import ch.heigvd.amt.adventurer.api.dto.Adventurer;
import ch.heigvd.amt.adventurer.api.dto.Quest;
import ch.heigvd.amt.adventurer.api.dto.QuestCreate;
import ch.heigvd.amt.adventurer.spec.helpers.Environment;
import ch.heigvd.amt.adventurer.spec.helpers.JwtManager;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.UUID;

public class ParticipationSteps {

    private Environment env;
    private JwtManager jwtManager;

    private String token;
    private Adventurer adventurer;
    private Quest quest;

    public ParticipationSteps(Environment env, JwtManager jwtManager) {
        this.env = env;
        this.jwtManager = jwtManager;
        this.token = "Bearer " + jwtManager.createToken("participation@pot.ato");
    }

    @Given("^an adventurer$")
    public void anAdventurer() throws Throwable {
        try {
            Environment.getApi().getApiClient().setApiKey(token);
            adventurer = Environment.getApi().createAdventurer(
                    new Adventurer().name(UUID.randomUUID().toString()).job("amt"));
        } catch (ApiException ignore) {
            Assert.fail("no adventurer");
        } finally {
            Environment.getApi().getApiClient().setApiKey(null);
        }
    }

    @Given("^an unknown adventurer$")
    public void anUnknownAdventurer() {
        adventurer = new Adventurer().name("Jack").job("amt");
    }

    @Given("^a quest$")
    public void aQuest() throws Throwable {
        try {
            Environment.getApi().getApiClient().setApiKey(token);

            Adventurer adv = Environment.getApi().createAdventurer(
                    new Adventurer().name(UUID.randomUUID().toString()).job("amt"));

            quest = Environment.getApi().createQuest(
                    new QuestCreate().adventurerName(adv.getName()).title("Quest"));

        } catch (ApiException ignore) {
            Assert.fail("no quest");
        } finally {
            Environment.getApi().getApiClient().setApiKey(null);
        }
    }

    @Given("^an ended quest$")
    public void anEndedQuest() throws Throwable {
        try {
            Environment.getApi().getApiClient().setApiKey(token);

            Adventurer adv = Environment.getApi().createAdventurer(
                    new Adventurer().name(UUID.randomUUID().toString()).job("amt"));

            quest = Environment.getApi().createQuest(
                    new QuestCreate().adventurerName(adv.getName()).title("Quest"));

            quest = Environment.getApi().endQuest((int) (long) quest.getId());

        } catch (ApiException ignore) {
            Assert.fail("no quest");
        } finally {
            Environment.getApi().getApiClient().setApiKey(null);
        }
    }

    @Given("^an unknown quest$")
    public void anUnknownQuest() {
        quest = new Quest().id(-12L).adventurerName(adventurer.getName());
    }

    @When("^I POST to /participation$")
    public void iPOSTToParticipation() throws Throwable {
        try {
            Environment.getApi().getApiClient().setApiKey(token);
            env.apiResponse = Environment.getApi()
                    .joinQuestWithHttpInfo(adventurer.getName(), (int) (long) quest.getId());
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

    @When("^I DELETE to /participation$")
    public void iDELETEToParticipation() throws Throwable {
        try {
            Environment.getApi().getApiClient().setApiKey(token);
            env.apiResponse = Environment.getApi()
                    .quitQuestWithHttpInfo(adventurer.getName(), (int) (long) quest.getId());
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

    @Given("^the adventurer participates to the quest$")
    public void givenTheAdventurerParticipatesToTheQuest() throws Throwable {
        try {
            Environment.getApi().getApiClient().setApiKey(token);
            Environment.getApi().joinQuest(adventurer.getName(), (int) (long) quest.getId());
        } catch (ApiException ignore) {
            Assert.fail("adventurer do not participate in quest");
        } finally {
            Environment.getApi().getApiClient().setApiKey(null);
        }
    }

    @Then("^the adventurer is participating to the quest$")
    public void theAdventurerIsParticipatingToTheQuest() {
        //TODO: check participating
    }

    @Then("^the adventurer is not participating to the quest$")
    public void theAdventurerIsNotParticipatingToTheQuest() {
        //TODO: check not participating
    }
}
