Feature: Creation of a user

  Background:
    Given there is a user management server

  Scenario: create a user
    Given I have a user information payload
    When I POST it to the /users endpoint
    Then I receive a 200 status code