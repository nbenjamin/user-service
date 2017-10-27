Feature: Add/view/edit group using rest endpoints

  Scenario Outline: add a new group
    Given application is up and running
    When client POST a request /core/users/<userId>/groups with groupName "<groupName>"
    Then group endpoint return httpStatus "<httpStatus>" in response
    Examples:
      | userId | groupName | httpStatus |
      | 1      | TestGroup | CREATED    |
      | 1      | TestGroup | CONFLICT   |
