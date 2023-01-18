@smoke_test
Feature: Login tests with Scenario Outline

  Scenario Outline: Go to <module> and verify title: <title>
    Given user is on the login page
    When user logs in as "<user_type>"
    And user navigates to "<tab>" and "<module>"
    Then user verifies that page title is "<title>"

    Examples:
      | user_type     | tab   | module          | title                                                              |
      | sales manager | Fleet | Vehicles        | All - Car - Entities - System - Car - Entities - System            |
      | sales manager | Fleet | Vehicles Model  | All - Vehicles Model - Entities - System - Car - Entities - System |
      | sales manager | Fleet | Accounts        | Dashboard                                                          |
      | sales manager | Fleet | Contacts        | Dashboard                                                          |
      | sales manager | Fleet | Calendar Events | Dashboard                                                          |

