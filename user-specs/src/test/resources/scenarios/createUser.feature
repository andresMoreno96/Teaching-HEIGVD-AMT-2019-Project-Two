Feature: Creation of a user

  Background:
    Given there is a user management server

  Scenario: create a user
    Given the user information create@pot.ato Pot Ato secret
    When I POST it to the /users endpoint
    Then I receive a 200 status code
    And I can retrieve the user information

  Scenario: create an existing user
    Given there is a user with the information create@pot.ato Pot Ato
    And the user information create@pot.ato Jack Eri secret
    When I POST it to the /users endpoint
    Then I receive a 400 status code
    And the user still has the full name Pot Ato

  Scenario: create a malformed user
    Given a malformed user
    When I POST it to the /users endpoint
    Then I receive a 400 status code
    And the user does not exist

  Scenario: create user with malformed email
    Given the user information create Pot Ato secret
    When I POST it to the /users endpoint
    Then I receive a 400 status code
    And the user does not exist