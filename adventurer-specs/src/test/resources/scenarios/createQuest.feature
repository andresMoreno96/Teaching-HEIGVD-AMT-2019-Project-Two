Feature: Creation of a Quest

  Background:
    Given there is a Quest management server
    Given a user jwt for "geto@pot.ato"

  Scenario: create a Quest
    Given the adventurer
    And the quest "slayer Dragon" with the objective of "killing"
    When I POST it into the /quests endpoint
    Then I receive a 200 status code
    And I can retrieve the Quest information



  Scenario: invalid token
    Given An invalid token
    Given the adventurer
    And the quest "slayer Dragon" with the objective of "killing"
    When I POST it into the /quests endpoint
    Then I receive a 401 status code

