Feature: Creation of a user

  Background:
    Given there is a user management server

  Scenario: create a user
    Given I have a user information payload
    When I POST it to the /users endpoint
    Then I receive a 200 status code

  Scenario: create user with a used email
    Given I have a user payload with a used email
    When I POST it to the /users endpoint
    Then I receive a 400 status code

  Scenario: create a user missing a required field
    Given I have a user missing a required field
    When I POST it to the /users endpoint
    Then I receive a 400 status code