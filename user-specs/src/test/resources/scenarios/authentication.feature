Feature: Authentication of a user

  Background:
    Given there is a user management server
    And there is a user with the credentials auth@pot.ato secret

  Scenario: user authentication success
    Given the user credentials auth@pot.ato secret
    When I authenticate at /authentication
    Then I receive a 200 status code
    And I receive an auth jwt for auth@pot.ato

  Scenario: user authentication fails with incorrect email
    Given the user credentials not.auth@pot.ato secret
    When I authenticate at /authentication
    Then I receive a 400 status code

  Scenario: user authentication fails with incorrect password
    Given the user credentials auth@pot.ato not.secret
    When I authenticate at /authentication
    Then I receive a 400 status code