Feature: SMS automated solutions
  Scenario Outline: provisioning order creation and fulfillment of SMS automated solution
    Given User create minchAccountID
    When User create Nova AccounID by passing minchAccountID as Input
    Then User create provisioning order by passing Nova accountID as billingAccountId for product- "<SMSAutomatedProduct>"
    Then User updates status of order from IN_DRAFT to IN_FULFILLMENT
    Then User calls Fulfillment service to fulfill the order
    Then verify status of order updated to FULFILLED
    Examples:
      | SMSAutomatedProduct |
      | SMS.1WAY-ROW.NOVA   |