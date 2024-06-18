Feature: ordering service test scenarios
  Scenario: Create an Order no Configurations
    Given User set createNewOrder endpoint for FirstScenario
    When no configurations is provided in body
    Then OrderId should not be created with orderID and OrderStatus IN_DRAFT

  Scenario: Create an Order with required Configurations
    Given User set createNewOrder endpoint for SecondScenario
    When required Configurations is provided in body
    Then OrderId should be created with orderID and OrderStatus IN_DRAFT for SecondScenario

  Scenario: Verify order is not created without product
    Given User set createNewOrder endpoint for FourthScenario
    When User do not provides productId and details in request body
    Then orderID should not be created