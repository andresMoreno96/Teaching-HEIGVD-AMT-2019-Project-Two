package ch.heigvd.amt.user.spec.steps;

import ch.heigvd.amt.user.ApiException;
import ch.heigvd.amt.user.api.dto.User;
import ch.heigvd.amt.user.spec.helpers.Environment;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class UserSteps {

    private Environment env;

    public UserSteps(Environment env) {
        this.env = env;
    }

    private User createUser() {
        return new User().email("pot@ato").firstName("Jack").lastName("Eri").password("secret");
    }

    @Given("^the user (.*)$")
    public void theUser(String email) throws Throwable {
        env.user = createUser().email(email);
    }

    @Given("the credentials (.*) (.*)")
    public void theCredentials(String email, String password) throws Throwable {
        env.user = createUser().email(email).password(password);
    }

    @Given("a malformed user")
    public void aMalformedUser() {
        env.user = new User().email("malformed@pot.ato");
    }

    @And("^there is a user with the credentials (.*) (.*)$")
    public void thereIsAUserWithTheCredentialsAuthPotAtoSecret(String email, String password) throws Throwable {
        try {
            Environment.getApi().createUser(createUser().email(email).password(password));
        } catch (ApiException ignore) {}
    }
}
