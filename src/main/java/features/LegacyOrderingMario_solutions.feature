Feature: Legacy ordering mario solutions
  #Scenario Outline: product - SMS.NEW-PRICELIST-MO-INBOUND-LEGACY.NOVA
  #  Given user set CreateNewOrder endpoint to create order for mario solutions- product
  #  When user provides product as "<marioproduct>"
  #  Then user should get orderID created for mario product
  #  Examples:
  #    | marioproduct                             |
  #    | SMS.NEW-PRICELIST-MO-INBOUND-LEGACY.NOVA |

  Scenario Outline: Legacy ordering mario solution complete flow
    Given user set endpoint for creating new minch account
    When user provides request body as "<displayName>"
    Then user should get minchAccountId as response from CreateNewminchAccount API
    Given user set endpoint for create new nova sms account
    When user provides minchAccountID as input
    Then user should get NovaAccountId created from CreateNewNovaSMSAccount
    Given user sets endpoint - CreateNewOrder
    When product provided should be "<marioproduct>"
    Then orderId should be created with status as IN_DRAFT
    Given user set endpoint to update status of OrderID to IN_FULFILLMENT
    Then status of orderId is updated to IN_FULFILLMENT
    Given User sets Fulfillment service endpoint to fulfill the order providing orderID
    Then response with success code OK should be received
    Then verify status of order should be FULFILLED
    Examples:
      | displayName | marioproduct                 |
      | Nam_test_01 | SMS.MO-NUMBER-MIGRATION.NOVA |