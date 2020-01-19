Feature: an adventurer quits a quest

  Scenario: an adventurer quits a quest
    Given an adventurer
    Given a quest
    Given the adventurer participates to the quest
    When I DELETE to /participation
    Then I receive a 200 status code
    Then the adventurer is not participating to the quest

  Scenario: an adventurer quits an unkown quest
    Given an adventurer
    Given an unknown quest
    When I DELETE to /participation
    Then I receive a 404 status code
    Then the adventurer is not participating to the quest

  Scenario: an unkown adventurer quits a quest
    Given an unknown adventurer
    Given a quest
    When I DELETE to /participation
    Then I receive a 404 status code
    Then the adventurer is not participating to the quest

  Scenario: an adventurer quits an ended quest
    Given an adventurer
    Given an ended quest
    When I DELETE to /participation
    Then I receive a 405 status code
    Then the adventurer is not participating to the quest