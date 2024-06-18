Feature: Account Services
  Scenario Outline: Get Account
    Given Host and port details with "<accountId>" is provided
    When call grpc api providing accountID
    Then validate all response code
    Examples: Testing with valid accountId
      | accountId                            |  |  |
      | 2297cd7e-9ba1-4755-b985-ea873a54aa90 |  |  |