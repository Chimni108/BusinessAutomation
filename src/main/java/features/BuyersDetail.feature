Feature: Get all buyers details
  Scenario: fetch buyer detail
    Given user set api endpoint
    When user send api request
    Then validate success response