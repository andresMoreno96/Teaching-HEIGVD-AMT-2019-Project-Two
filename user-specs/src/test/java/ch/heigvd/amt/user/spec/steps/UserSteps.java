package ch.heigvd.amt.user.spec.steps;

import ch.heigvd.amt.user.ApiException;
import ch.heigvd.amt.user.api.dto.User;
import ch.heigvd.amt.user.api.dto.UserNoPassword;
import ch.heigvd.amt.user.spec.helpers.Environment;
import ch.heigvd.amt.user.spec.helpers.JwtManager;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class UserSteps {

    private Environment env;
    private JwtManager jwtManager;

    public UserSteps(Environment env, JwtManager jwtManager) {
        this.env = env;
        this.jwtManager = jwtManager;
    }

    @Given("^the user information (.*) (.*) (.*) (.*)$")
    public void theUserInformation(String email, String firstName, String lastName, String password) throws Throwable {
        env.user = new User()
                .email(email).password(password)
                .firstName(firstName).lastName(lastName);
    }

    @Given("the user credentials (.*) (.*)")
    public void theCredentials(String email, String password) throws Throwable {
        env.user = new User().email(email).password(password);
    }

    @Given("a malformed user")
    public void aMalformedUser() {
        env.user = new User().email("malformed@pot.ato");
    }

    @Given("^there is a user with the credentials (.*) (.*)$")
    public void thereIsAUserWithTheCredentials(String email, String password) throws Throwable {
        try {
            Environment.getApi().createUser(
                    new User().email(email).password(password)
                    .firstName("Jack").lastName("Eri"));
        } catch (ApiException ignore) {}
    }

    @Given("^there is a user with the information (.*) (.*) (.*)$")
    public void thereIsAUserWithTheInformation(String email, String firstName, String lastName) throws Throwable {
        UserNoPassword user = fetchUser(email);

        if (user != null) {
            Assert.assertEquals(email, user.getEmail());
            Assert.assertEquals(firstName, user.getFirstName());
            Assert.assertEquals(lastName, user.getLastName());
        } else {
            try {
                Environment.getApi().createUser(
                        new User().email(email).password("secret")
                                .firstName(firstName).lastName(lastName));
            } catch (ApiException ignore) {}
        }
    }

    @Then("^I can retrieve the user information$")
    public void iCanRetrieveTheUserInformation() throws Throwable {
        UserNoPassword user = fetchUser(env.user.getEmail());

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getEmail(), env.user.getEmail());
        Assert.assertEquals(user.getFirstName(), env.user.getFirstName());
        Assert.assertEquals(user.getLastName(), env.user.getLastName());
    }

    @Then("^the user still has the full name (.*) (.*)$")
    public void theUserStillHasTheFullName(String firstName, String lastName) throws Throwable {
        UserNoPassword user = fetchUser(env.user.getEmail());

        Assert.assertNotNull(user);
        Assert.assertEquals(firstName, user.getFirstName());
        Assert.assertEquals(lastName, user.getLastName());
    }

    @Then("^the user does not exist$")
    public void theUserDoesNotExist() throws Throwable {
        Assert.assertNull(fetchUser(env.user.getEmail()));
    }

    private UserNoPassword fetchUser(String email) throws Throwable {
        try {
            Environment.getApi().getApiClient().setApiKey("Bearer " + jwtManager.createToken(email));
            return Environment.getApi().getUser(email);
        } catch(ApiException e) {
            return null;
        } finally {
            Environment.getApi().getApiClient().setApiKey(null);
        }
    }
}
