package com.minch.BPA.springbootcucumber.stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ConfScenarioStepDef {
    @Given("FirstOrderLine SMS_NOVA_ACCOUNT with {string}")
    public void firstorderlineSMS_NOVA_ACCOUNTWith(String arg0) {
    }

    @Given("SecondOrderLine having conf for product {string} with {string}")
    public void secondorderlineHavingConfForProductWith(String arg0, String arg1) {
    }

    @Then("{string} assets should be created for Nova account and {string} assets should be created for Projects")
    public void assetsShouldBeCreatedForNovaAccountAndAssetsShouldBeCreatedForProjects(String arg0, String arg1) {
    }

    @Given("FirstOrderLine having conf for product {string} with {string}and SecondOrderLine having conf for product {string} with {string}")
    public void firstorderlineHavingConfForProductWithAndSecondOrderLineHavingConfForProductWith(String arg0, String arg1, String arg2, String arg3) {
    }

    @Then("Error should be {string}")
    public void errorShouldBe(String arg0) {
    }

    @Given("FirstOrderLine having conf for {string} with {string} and SecondOrderLine having conf for {string} with {string}")
    public void firstorderlineHavingConfForWithAndSecondOrderLineHavingConfForWith(String arg0, String arg1, String arg2, String arg3) {
    }

    @Given("FirstOrderLine having conf with {string}")
    public void firstorderlineHavingConfWith(String arg0) {
    }

    @When("SecondOrderLine having conf with {string} with Type as ASSET_CONFIG_LINK_POP referring to SMS Nova Account")
    public void secondorderlineHavingConfWithWithTypeAsASSET_CONFIG_LINK_POPReferringToSMSNovaAccount(String arg0) {
    }

    @When("ThirdOrderLine having conf with {string} as common project referring to SecondOrderLine with Type as ASSET_CONFIG_LINK_POP")
    public void thirdorderlineHavingConfWithAsCommonProjectReferringToSecondOrderLineWithTypeAsASSET_CONFIG_LINK_POP(String arg0) {
    }

    @Then("all order line should be executed with asset created with status of order should be Succeeded")
    public void allOrderLineShouldBeExecutedWithAssetCreatedWithStatusOfOrderShouldBeSucceeded() {
    }

    @When("SecondOrderLine having conf with {string} with Type as ASSET_CONFIG_LINK_POP referring to SMS Nova Account FirstOrderLine")
    public void secondorderlineHavingConfWithWithTypeAsASSET_CONFIG_LINK_POPReferringToSMSNovaAccountFirstOrderLine(String arg0) {
    }

    @When("ThirdOrderLine having conf with {string} referring to SecondOrderLine")
    public void thirdorderlineHavingConfWithReferringToSecondOrderLine(String arg0) {
    }

    @When("FourthOrderLine having conf with {string} referring to SecondOrderLine")
    public void fourthorderlineHavingConfWithReferringToSecondOrderLine(String arg0) {
    }

    @Then("FourthOrderLine should not execute and status should be failed")
    public void fourthorderlineShouldNotExecuteAndStatusShouldBeFailed() {
    }

    @Then("status of FirstOrderLine SecondOrderLine ThirdOrderLine should be Succeeded")
    public void statusOfFirstOrderLineSecondOrderLineThirdOrderLineShouldBeSucceeded() {
    }

    @Given("FirstOrderLine having conf with {string} with {string}")
    public void firstorderlineHavingConfWithWith(String arg0, String arg1) {
    }

    @When("SecondOrderLine having conf with {string} with Type as ASSET_CONFIG_LINK_NOP referring to SMS.NOVA.ACCOUNT with {string}")
    public void secondorderlineHavingConfWithWithTypeAsASSET_CONFIG_LINK_NOPReferringToSMSNOVAACCOUNTWith(String arg0, String arg1) {
    }

    @When("ThirdOrderLine having conf with {string} referring to SecondOrderLine with {string}")
    public void thirdorderlineHavingConfWithReferringToSecondOrderLineWith(String arg0, String arg1) {
    }

    @When("FourthOrderLine having conf with {string} referring to SecondOrderLine with {string}")
    public void fourthorderlineHavingConfWithReferringToSecondOrderLineWith(String arg0, String arg1) {
    }

    @Then("all orderLine should be executed and status should be succeeded")
    public void allOrderLineShouldBeExecutedAndStatusShouldBeSucceeded() {
    }

    @When("SecondOrderLine having conf with {string}<seven_qty>\" with type as ASSET_CONFIG_LINK_NOP referring to SMS_NOVA_ACCOUNT")
    public void secondorderlineHavingConfWithSeven_qtyWithTypeAsASSET_CONFIG_LINK_NOPReferringToSMS_NOVA_ACCOUNT(String arg0) throws Throwable {    // Write code here that turns the phrase above into concrete actions    throw new cucumber.api.PendingException();}
    }

    @When("ThirdOrderLine having conf with {string} referring to common_projects with five qty for ISO country code SE and two qty for ISO country code GB")
    public void thirdorderlineHavingConfWithReferringToCommon_projectsWithFiveQtyForISOCountryCodeSEAndTwoQtyForISOCountryCodeGB(String arg0) {
    }

    @Then("All orderline should be fulfilled and status should be Succeeded")
    public void allOrderlineShouldBeFulfilledAndStatusShouldBeSucceeded() {
    }

    @Given("FirstOrderLine is having dependency on SecondOrderLine")
    public void firstorderlineIsHavingDependencyOnSecondOrderLine() {
    }

    @Given("ThirdOrderLine is not having any dependency")
    public void thirdorderlineIsNotHavingAnyDependency() {
    }

    @Then("ThirdOrderLine should execute first and Then SecondOrderLine and FirstOrderLine should execute")
    public void thirdorderlineShouldExecuteFirstAndThenSecondOrderLineAndFirstOrderLineShouldExecute() {
    }

}
