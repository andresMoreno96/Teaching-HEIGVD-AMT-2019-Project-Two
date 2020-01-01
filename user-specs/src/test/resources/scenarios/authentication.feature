Feature: Authentication of a user

  Background:
    Given there is a user management server
    And there is a user with the credentials auth@pot.ato secret

  Scenario: user authentication success
    Given the credentials auth@pot.ato secret
    When I authenticate at /authentication
    Then I receive a 200 status code
    And I receive a jwt with the email pot@ato

  Scenario: user authentication fails with incorrect credentials
    Given the credentials not.pot@ato secret
    When I authenticate at /authentication
    Then I receive a 400 status code