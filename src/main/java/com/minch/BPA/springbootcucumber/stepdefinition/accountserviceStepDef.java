package com.minch.BPA.springbootcucumber.stepdefinition;

import com.minch.BPA.springbootcucumber.grpc.Client;
import com.minch.account.v1alpha1.Account;
import com.minch.account.v1alpha1.GetAccountRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class accountserviceStepDef {
    @Autowired
    Client clientObject;

    GetAccountRequest.Builder getrequest=GetAccountRequest.newBuilder();
    Account accountresponse;

  //   @Test
    @Given("Host and port details with {string} is provided")
    public void hostAndPortDetailsAreProvided (String accountID) {
        getrequest.setAccountId(accountID).build();
        log.info(String.valueOf(getrequest));
        log.info("assuming host and port are provided");
    }

  //  @Test
    @When("^call grpc api providing accountID$")
    public void callGrpcApiProvidingAccountIDIsProvided() {
        log.info("I am into When");
        accountresponse=clientObject.getAccounts(getrequest.build());
        log.info("Just check Client Objecty");
        //log.info("account response: "+accountresponse);
    }

  //  @Test
    @Then("^validate all response code$")
    public void validateAllResponseCode() {
        log.info("\n GRPC response received" +accountresponse);
    //log.info(String.valueOf(accountresponse));

    }


}
