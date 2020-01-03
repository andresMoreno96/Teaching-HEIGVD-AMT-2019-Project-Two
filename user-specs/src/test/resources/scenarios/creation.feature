Feature: Creation of a user

  Background:
    Given there is a user management server

  Scenario: create a user
    Given the user pot@ato
    When I POST it to the /users endpoint
    Then I receive a 200 status code

  Scenario: create an existing user
    Given the user pot@ato
    When I POST it to the /users endpoint
    Then I receive a 400 status code

  Scenario: create a malformed user
    Given a malformed user
    When I POST it to the /users endpoint
    Then I receive a 400 status code

  Scenario: create user with malformed email
    Given the user potato
    When I POST it to the /users endpoint
    Then I receive a 400 status code