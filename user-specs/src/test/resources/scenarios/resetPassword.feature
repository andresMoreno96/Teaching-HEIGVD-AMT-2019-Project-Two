Feature: Creation of a password reset request

  Background:
    Given there is a user management server
    And there is a user with the credentials request@pot.ato secret

  Scenario: create password reset request
    When I POST to /users/request@pot.ato/password-reset
    Then I receive a 200 status code
    And I receive an email for request@pot.ato with a jwt
    And the jwt is for a password reset for request@pot.ato

  Scenario: create password reset request for an unknown user
    When I POST to /users/not.request@pot.ato/password-reset
    Then I receive a 404 status code
    And no email si sent