package com.minch.BPA.springbootcucumber.stepdefinition;

import io.cucumber.java.Before;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.messages.types.Scenario;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConfStepDef {
    public static String tokenGenerated;
    RequestSpecification createOrderRequest;
    public static String  provisioningOrderID;
    private Response OrderProvisioingResponse;
    public static Scenario scenario;

    @Before
    public void generateOAuthtoken()
    {
        RestAssured.baseURI="https://public.token-service.common-auth.staging.minch.com";
        Response response1= RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .headers("Authorization","Basic YnBhLXByb3Zpc2lvbmluZy1zdGFnaW5nLXRlc3Q6cF84QkhtTjllYWI3N2p6YmNCUVN1eWMwR3g=")
                .formParam("grant_type","client_credentials")
                .post("https://public.token-service.common-auth.staging.minch.com/oauth2/token");
        // System.out.println(response1.jsonPath().prettify());
        String jsonString=response1.getBody().asString();
        tokenGenerated= JsonPath.from(jsonString).get("access_token");
        //  System.out.println("TokenGenerated: "+tokenGenerated);
    }
    @Given("Request body with orderLines for product SMS_NOVA_ACCOUNT and COMMON_PROJECT with same quantity is passed")
    public void requestBodyWithOrderLinesForProductSMS_NOVA_ACCOUNTAndCOMMON_PROJECTWithSameQuantityIsPassed() throws IOException {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
        FileInputStream fileInputStream = new FileInputStream(new File("src/main/java/com/minch/BPA/springbootcucumber/JSONRequestFile/SMSAutomatedSolutions.json"));
        createOrderRequest = RestAssured.given();
        OrderProvisioingResponse=createOrderRequest.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(IOUtils.toString(fileInputStream,"UTF-8"))
                .post("/v1alpha1/orders")
                .then().extract().response();
      //  ConfStepDef.scenario.setDescription("Error message"+OrderProvisioingResponse.getStatusLine());
//        ConfStepDef.scenario.setDescription("Error message"+OrderProvisioingResponse.jsonPath().get("message"));
        int statCode=OrderProvisioingResponse.getStatusCode();

        System.out.println("SMSAutomatedSolutions" +OrderProvisioingResponse.getStatusLine());
        System.out.println("Error message: "+OrderProvisioingResponse.jsonPath().get("message"));

        Assert.assertEquals(200,statCode);

        System.out.println("status line of CreateNewOrder response" +OrderProvisioingResponse.statusLine());
        provisioningOrderID=OrderProvisioingResponse.jsonPath().get("id");
        System.out.println("OrderID: "+provisioningOrderID);
        // System.out.println(OrderProvisioingResponse.jsonPath().prettify());
    }

    @Then("same quantity asset should be created for SMS_NOVA_ACCOUNT and COMMON_PROJECT")
    public void sameQuantityAssetShouldBeCreatedForSMS_NOVA_ACCOUNTAndCOMMON_PROJECT() {
        System.out.println("SMSAutomatedSolutions" +OrderProvisioingResponse.getStatusLine());
        System.out.println("Error message: "+OrderProvisioingResponse.jsonPath().get("message"));
        ConfStepDef.scenario.setDescription("Error message"+OrderProvisioingResponse.getStatusLine());
        ConfStepDef.scenario.setDescription("Error message"+OrderProvisioingResponse.jsonPath().get("message"));
    }
}
