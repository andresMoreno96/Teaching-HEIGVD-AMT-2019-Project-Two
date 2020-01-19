Feature: Suppression of a Quest

  Background:
    Given there is a Quest management server
    Given a user jwt for "geto@pot.ato"

  Scenario: delete a Quest
    Given the adventurer
    And the already existing quest "slayer Dragon" with the objective of "killing"
    When I DELETE  into the /quests endpoint
    Then I receive a 200 status code

  Scenario: delete an in-existent Quest
    Given the adventurer
    And the already existing quest "slayer Dragon" with the objective of "killing"
    When I DELETE  into the /quests endpoint
    Then I receive a 400 status code
    And I can retrieve the Quest information



  Scenario: invalid token
    Given An invalid token
    Given the adventurer
    And the already existing quest "slayer Dragon" with the objective of "killing"
    When I DELETE  into the /quests endpoint
    Then I receive a 401 status code

