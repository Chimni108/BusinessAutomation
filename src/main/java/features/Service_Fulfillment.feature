Feature: Status of order
  Scenario Outline: Update status of orderID
    Given User sets endpoint with orderId to update status of order
    When status of order should be "<previousstatus>"
    Then service is updating status of orderId to "<Status>"
    Examples:
      | Status           | previousstatus   |  |
      | IN_REVIEW        | IN_DRAFT         |  |
      | IN_APPROVAL      | IN_REVIEW        |  |
      | IN_SIGNATURE     | IN_APPROVAL      |  |
      | IN_CONFIGURATION | IN_SIGNATURE     |  |
      | IN_FULFILLMENT   | IN_CONFIGURATION |  |
      | FULFILLED        | IN_FULFILLMENT   |  |