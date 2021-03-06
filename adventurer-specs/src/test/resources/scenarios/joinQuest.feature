Feature: an adventurer joins a quest

  Scenario: an adventurer joins a quest
    Given an adventurer
    Given a quest
    When I POST to /participation
    Then I receive a 200 status code
    Then the adventurer is participating to the quest

  Scenario: an adventurer joins an unknown quest
    Given an adventurer
    Given an unknown quest
    When I POST to /participation
    Then I receive a 404 status code
    Then the adventurer is not participating to the quest

  Scenario: an unknown adventurer joins a quest
    Given an unknown adventurer
    Given a quest
    When I POST to /participation
    Then I receive a 404 status code
    Then the adventurer is not participating to the quest

  Scenario: an adventurer joins an ended quest
    Given an adventurer
    Given an ended quest
    When I POST to /participation
    Then I receive a 405 status code
    Then the adventurer is not participating to the quest