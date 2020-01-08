Feature: Get user information

  Background:
    Given there is a user management server
    And there is a user with the information get@pot.ato Pot Ato

  Scenario: get user information
    Given a user jwt for get@pot.ato
    When I GET to /users/get@pot.ato
    Then I receive a 200 status code
    And I got the user information get@pot.ato Pot Ato

  Scenario: get unknown user information
    Given a user jwt for not.get@pot.ato
    When I GET to /users/not.get@pot.ato
    Then I receive a 404 status code

  Scenario: get unauthorized user information
    Given a user jwt for not.get@pot.ato
    When I GET to /users/get@pot.ato
    Then I receive a 401 status code

  Scenario: get user information with invalid jwt
    Given an invalid jwt
    When I GET to /users/get@pot.ato
    Then I receive a 401 status code
