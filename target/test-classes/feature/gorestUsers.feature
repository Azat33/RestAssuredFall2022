Feature: Gorest user

  @getAllUsers
  Scenario: Get all users
    When all users are requested
    Then a status code 200 is returned
    And 10 users are returned

  @createUser
  Scenario: Create user
    When the following user is created
      | name     | email                | status | gender |
      | John Doe | john.doe10@gmail.com | active | male   |
    Then a status code 201 is returned
    And the following user is returned
      | name     | email                | status | gender |
      | John Doe | john.doe10@gmail.com | active | male   |

  @deleteUser
  Scenario: Delete user
    When the user is deleted
    Then a status code 204 is returned