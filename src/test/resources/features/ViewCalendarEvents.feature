@view_calendar_events
Feature: View all calendar events
  As user, I want to see all calendar events as a table

  @DataTable1
  Scenario: User permissions
    Given user is on the login page
    When user logs in as a sales manager
    When user navigates to "Activities" and "Calendar Events"
    Then View Per Page menu should have following options
      | 10  |
      | 25  |
      | 50  |
      | 100 |