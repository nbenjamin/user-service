Feature: Add/view/edit user using rest endpoints

  Scenario Outline: add a new user
    Given application is up and running
    When client POST a request /core/users with the following information
      | firstName   | LastName   | userName   | password   |
      | <firstName> | <lastName> | <username> | <password> |
    Then return httpStatus "<httpStatus>" in response
    Examples:
      | firstName | lastName | username | password | httpStatus |
      | Ryan      | Benjamin | ryan     | abc123   | CREATED    |
      | Adam      | Benjamin | ryan     | xyz123   | CONFLICT   |
      | Adam      | Benjamin | adam     | xyz123   | CREATED    |

  Scenario Outline: view existing user
    Given application is up and running
    When client GET a request user with resource url /core/users/"<firstName>"/"<lastName>"
    Then return User with httpStatus "<httpStatus>"
    And  verify the username "<userName>"
    Examples:
      | firstName | lastName  | userName | httpStatus |
      | Ryan      | Benjamin  | ryan     | OK         |
      | Adam      | Benjamin  | adam     | OK         |
      | Invalid   | dontExist | null     | NOT_FOUND  |
