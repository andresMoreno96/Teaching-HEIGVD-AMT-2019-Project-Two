Feature: change the user password

  Background:
    Given there is a user management server
    And there is a user with the credentials change1@pot.ato 123
    And there is a user with the credentials change2@pot.ato 123

  Scenario: change password
    Given a password reset token for change1@pot.ato
    When I POST to /passwords with the new password secret
    Then I receive a 200 status code
    And I can authenticate with the credentials change1@pot.ato secret

  Scenario: change password without token
    When I POST to /passwords without token with the new password secret
    Then I receive a 401 status code

  Scenario: change password with user jwt
    Given a user token for change2@pot.ato 123
    When I POST to /passwords with the new password secret
    Then I receive a 401 status code
    And I cannot authenticate with the credentials change2@pot.ato secret

  Scenario: change password with invalid token
    Given an invalid jwt
    And I POST to /passwords with the new password secret
    Then I receive a 401 status code