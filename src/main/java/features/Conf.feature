Feature: Configuration related scenarios with SMS Automated solutions
  Scenario: Verify 1:1 relation for fulfillment in quantity of project with quantity of Nova Account is followed
    Given Request body with orderLines for product SMS_NOVA_ACCOUNT and COMMON_PROJECT with same quantity is passed
    Then same quantity asset should be created for SMS_NOVA_ACCOUNT and COMMON_PROJECT