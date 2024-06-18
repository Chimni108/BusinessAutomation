Feature: Configuration-related-scenarios
  Scenario : Verify 1:1 relation for fulfillment in quantity of project with quantity of Nova Account is followed
    Given FirstOrderLine SMS_NOVA_ACCOUNT with "<qty>"
    Given SecondOrderLine having conf for product "<Common_Projects>" with "<qty>"
    Then "<qty>" assets should be created for Nova account and "<qty>" assets should be created for Projects


  Scenario Outline: Verify error when there is no 1:1 relation in quantity of project and nova account
    Given FirstOrderLine having conf for product "<SMS_NOVA_ACCOUNT>" with "<five_qty>"and SecondOrderLine having conf for product "<Common_Project>" with "<two_qty>"
    Then Error should be "<Error Message>"
    Examples:
      | SMS_NOVA_ACCOUNT         | Common_Project      | Error Message                                                   | five_qty | two_qty |
      | SMS.ACCOUNT.NOVA         | COMMON.PROJECT.NOVA | Required Quantity for Project dependency is greater than supply | 5        | 2       |

  Scenario Outline: Verify error when Nova account requested is lesser than project requested
    Given FirstOrderLine having conf for "<SMS_Nova_Account>" with "<one_qty>" and SecondOrderLine having conf for "<Common_Project>" with "<five_quantity>"
    Then Error should be "<Error Message>"
    Examples:
      | SMS_Nova_Account | Common_Project      | Error Message                                                         | one_qty | five_quantity |
      | SMS.ACCOUNT.NOVA | COMMON.PROJECT.NOVA | Required Quantity for NovaAccountId dependency is greater than supply | 1       | 5             |

  Scenario Outline: Verify POP scenario where only 1 dependency can refer to ASSET_CONFIG_LINK_POP
    Given FirstOrderLine having conf with "<SMS_Nova_Account>"
    When SecondOrderLine having conf with "<common_project>" with Type as ASSET_CONFIG_LINK_POP referring to SMS Nova Account
    When ThirdOrderLine having conf with "<SMS_way_row_NOVA>" as common project referring to SecondOrderLine with Type as ASSET_CONFIG_LINK_POP
    Then all order line should be executed with asset created with status of order should be Succeeded
    Examples:
    | SMS_Nova_Account | common_project      | SMS_way_row_NOVA  |
    | SMS.ACCOUNT.NOVA | COMMON.PROJECT.NOVA | SMS.1WAY-ROW.NOVA |

  Scenario Outline: Verify POP scenario where more than 1 dependency cannot have reference to same POP config ID
    Given FirstOrderLine having conf with "<SMSNovaAccount>"
    When SecondOrderLine having conf with "<common_project>" with Type as ASSET_CONFIG_LINK_POP referring to SMS Nova Account FirstOrderLine
    When ThirdOrderLine having conf with "<common_project>" referring to SecondOrderLine
    When FourthOrderLine having conf with "<common_project>" referring to SecondOrderLine
    Then FourthOrderLine should not execute and status should be failed
    Then status of FirstOrderLine SecondOrderLine ThirdOrderLine should be Succeeded
    Examples:
      | SMSNovaAccount   | common_project      |
      | SMS.ACCOUNT.NOVA | COMMON.PROJECT.NOVA |

  Scenario Outline: Verify NOP scenarios where more than 1 dependency can have reference to same NOP ConfigID
    Given FirstOrderLine having conf with "<SMS_Nova_Account>" with "<two_qty>"
    When SecondOrderLine having conf with "<COMMON_PROJECT>" with Type as ASSET_CONFIG_LINK_NOP referring to SMS.NOVA.ACCOUNT with "<two_qty>"
    When ThirdOrderLine having conf with "<COMMON_PROJECT>" referring to SecondOrderLine with "<two_qty>"
    When FourthOrderLine having conf with "<COMMON_PROJECT>" referring to SecondOrderLine with "<two_qty>"
    Then all orderLine should be executed and status should be succeeded
    Examples:
      | SMS_Nova_Account | COMMON_PROJECT      | two_qty |
      | SMS.ACCOUNT.NOVA | COMMON.PROJECT.NOVA | 2       |

  Scenario Outline: verify quantity distribution of projects to different countries in same Conf
    Given FirstOrderLine having conf with "<SMS_NOVA_ACCOUNT>" with "<seven_qty>"
    When SecondOrderLine having conf with "<common_Projects> with "<seven_qty>" with type as ASSET_CONFIG_LINK_NOP referring to SMS_NOVA_ACCOUNT
    When ThirdOrderLine having conf with "<SMS_WAY_ROW_NOVA>" referring to common_projects with five qty for ISO country code SE and two qty for ISO country code GB
    Then All orderline should be fulfilled and status should be Succeeded
    Examples:
      | SMS_NOVA_ACCOUNT | common_Projects     | SMS_WAY_ROW_NOVA    |
      | SMS.ACCOUNT.NOVA | COMMON.PROJECT.NOVA | SMS.1WAY-ROW.NOVA   |

  Scenario: Verify execution order of OrderLines
    Given FirstOrderLine is having dependency on SecondOrderLine
    Given ThirdOrderLine is not having any dependency
    Then ThirdOrderLine should execute first and Then SecondOrderLine and FirstOrderLine should execute