Feature: Add/view/edit user using rest endpoints

  Scenario Outline: add and view user
    Given application is up and running
    When client POST a request /core/users with the following information
      | firstName   | LastName   | userName   | password   |
      | <firstName> | <lastName> | <username> | <password> |
    Then return httpStatus "<httpStatus>" in response
#    And makes a call to get User by "<firstName>" and "<lastName>"
#    Then validate the user response with the below User details
#      | firstName   | LastName   | userName   | password   |
#      | <firstName> | <lastName> | <username> | <password> |

    Examples:
      | firstName | lastName | username | password | httpStatus |
      | Ryan      | Benjamin | ryan     | abc123   | CREATED    |
      | Adam      | Benjamin | ryan     | xyz123   | CONFLICT   |