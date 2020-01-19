Feature: Creation of an Adventurer

#  Background:
#    Given there is an adventurer management server
#
#  Scenario: create an adventurer
#    Given a user jwt for "get@pot.ato"
#    And  the adventurer "Tony1998" is a "rune master"
#    When I POST it into the /adventurers endpoint
#    Then I receive a 200 status code
#    And I can retrieve the adventurer information
#
#  Scenario: create an already existing adventurer
#    Given there is an adventurer with the named "Tony1998" and he is a "rune master"
#    And  the adventurer "Tony1998" is a "fisher-man"
#    When I POST it into the /adventurers endpoint
#    Then I receive a 400 status code
#    And the adventurer still has the job "rune master"
#
#  Scenario: create a malformed adventurer
#    Given a malformed adventurer
#    When I POST it into the /adventurers endpoint
#    Then I receive a 400 status code
#    And the adventurer does not exist
#
#  Scenario: invalid token
#    Given  the adventurer "Tony1998" is a "rune master"
#    When I POST it into the /adventurers endpoint
#    Then I receive a 401 status code
#    And I can retrieve the adventurer information