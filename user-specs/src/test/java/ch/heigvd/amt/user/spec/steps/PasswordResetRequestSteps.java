package ch.heigvd.amt.user.spec.steps;

import ch.heigvd.amt.user.ApiException;
import ch.heigvd.amt.user.spec.helpers.Environment;
import cucumber.api.java.en.When;

public class PasswordResetRequestSteps {

    private Environment env;

    public PasswordResetRequestSteps(Environment env) {
        this.env = env;
    }

    @When("^I POST to /users/(.*)/password-reset")
    public void iPostToUsersPasswordReset(String email) throws Throwable {
        try {
            env.apiResponse = Environment.getApi().resetPasswordWithHttpInfo(email);
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
}
