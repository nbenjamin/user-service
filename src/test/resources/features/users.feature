Feature: Add/view/edit user using rest endpoints

  Scenario Outline: add and view user
    Given application is up and running
    When client POST a request /core/users with the following information
      | firstName   | LastName   | userName   | password   |
      | <firstName> | <lastName> | <username> | <password> |
    Then return httpstatus <httpStatus>
    Then

    Examples:
      | firstName | lastName | username | password | httpStatus |
      | Ryan      | Benjamin | ryan     | abc123   | CREATED    |