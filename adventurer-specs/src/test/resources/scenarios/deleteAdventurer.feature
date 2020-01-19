Feature: Suppression of an Adventurer

  Background:
    Given there is an adventurer management server
    Given a user jwt for "get@pot.ato"

  Scenario: delete an adventurer
    Given  the adventurer "Tony1998" is a "rune master"
    When I DELETE it into the /adventurers endpoint
    Then I receive a 200 status code

  Scenario: delete an in-existent adventurer
    Given the adventurer "Tony1998" is a "rune master"
    When I DELETE it into the /adventurers endpoint
    Then I receive a 400 status code
    And the adventurer does not exist

  Scenario: delete with an invalid token
    Given An invalid token
    Given  the adventurer "Tony1998" is a "rune master"
    When I POST it into the /adventurers endpoint
    Then I receive a 401 status code

