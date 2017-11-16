Feature: Add/view/edit group using rest endpoints

  Scenario Outline: add a new group
    Given application is up and running
    When client POST a request /core/users/<userId>/groups with groupName "<groupName>"
    Then group endpoint return httpStatus "<httpStatus>" in response
    Examples:
      | userId | groupName | httpStatus |
      | 1      | TestGroup | CREATED    |
      | 1      | TestGroup | CONFLICT   |
      | 1      | NewGroup  | CREATED    |
      | 2      | DemoGroup | CREATED    |

  Scenario : add users to an existing group and list
    Given application is up and running
    When client PUT a request /core/users/<adminuserId>/groups/<groupId> with UserIds "<userIds>"
    Then endpoint return httpStatus "<httpStatus>" in response
    Then verify list of users added to the group by call GET
  Examples:
  | adminuserId | groupId | userIds | httpStatus |
  | 1           | 1       |  2,3    | CREATED    |
  | 1           | 2       |  2      | CREATED    |

