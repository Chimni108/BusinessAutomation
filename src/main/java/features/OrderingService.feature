Feature: Ordering service
  Scenario: create new order through ordering service
    Given user set CreateNewOrder endpoint
    When user send CreateNewOrder request body
    Then validate success response and orderId