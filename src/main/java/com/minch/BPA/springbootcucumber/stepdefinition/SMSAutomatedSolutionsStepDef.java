package com.minch.BPA.springbootcucumber.stepdefinition;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SMSAutomatedSolutionsStepDef {
    public static String tokenGenerated;
    private Response minchAccountResponse;
    public static String minchAccountID;
    RequestSpecification request;   
    private Response NovaAccountResponse;
    public static String novaAccountID;
    RequestSpecification createOrderRequest;
    private Response OrderProvisioingResponse;
    RequestSpecification UpdateOrderRequest;
    private Response updateOrderProvisioingResponse;
    public static String  provisioningOrderID;
    RequestSpecification FulfillmentRequest;
    private Response fulfillmentResponse;
    public static String  fullfillmentStatus;
    RequestSpecification getOrderIDRequest;
    private Response getOrderIDResponse;


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

    @Given("User create minchAccountID")
    public void userCreateminchAccountID() {
        RestAssured.baseURI="https://provisioning.eu1tst.bpa.staging.minch.com:443";
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject value = factory.createObjectBuilder().add("displayName","TestSMSAutomatedProduct").build();

        request = RestAssured.given();
        minchAccountResponse=request.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(value.toString())
                .post("/v1alpha1/accounts")
                .then().assertThat().statusCode(200).extract().response();

        minchAccountID = minchAccountResponse.jsonPath().get("minchAccountId");
        System.out.println("minch accountId: " +minchAccountID);
    }

    @When("User create Nova AccounID by passing minchAccountID as Input")
    public void userCreateNovaAccounIDByPassingminchAccountIDAsInput() {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject value = factory.createObjectBuilder()
                .add("displayName","Test1_automation")
                .add("minchAccountId",minchAccountID)
                .add("billingAccount",factory.createObjectBuilder()
                        .add("countryCode","GB")
                        .add("currencyCode","dol")
                ).build();
        request = RestAssured.given();
        NovaAccountResponse=request.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(value.toString())
                .post("/v1alpha1/novasms/accounts")
                .then().assertThat().statusCode(200).extract().response();

        novaAccountID=NovaAccountResponse.jsonPath().get("novaAccountId");
        System.out.println("nova account Id "+novaAccountID);
    }

    @Then("User create provisioning order by passing Nova accountID as billingAccountId for product- {string}")
    public void userCreateProvisioningOrderByPassingNovaAccountIDAsBillingAccountIdForProduct(String SMSAutomatedProduct) throws IOException {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
        FileInputStream fileInputStream = new FileInputStream(new File("src/main/java/com/minch/BPA/springbootcucumber/JSONRequestFile/SMSTestService.json"));

        createOrderRequest = RestAssured.given();
        OrderProvisioingResponse=createOrderRequest.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(IOUtils.toString(fileInputStream,"UTF-8"))
                .post("/v1alpha1/orders")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("status line of CreateNewOrder response" +OrderProvisioingResponse.statusLine());
        provisioningOrderID=OrderProvisioingResponse.jsonPath().get("id");
        System.out.println("OrderID: "+provisioningOrderID);
        // System.out.println(OrderProvisioingResponse.jsonPath().prettify());
    }

    @Then("User updates status of order from IN_DRAFT to IN_FULFILLMENT")
    public void userUpdatesStatusOfOrderFromIN_DRAFTToIN_FULFILLMENT() throws JSONException {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
        UpdateOrderRequest = RestAssured.given();
        JSONObject jsonObjectResponse= new JSONObject(OrderProvisioingResponse.getBody().asString());
        jsonObjectResponse.put("status","IN_FULFILLMENT");
        System.out.println("req json object"+jsonObjectResponse.toString());

        updateOrderProvisioingResponse=UpdateOrderRequest.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(jsonObjectResponse.toString())
                .patch("/v1alpha1/orders/"+provisioningOrderID)
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("Status of order is updated to IN_FULFILLMENT");
        System.out.println("status line of PATCH response" +updateOrderProvisioingResponse.statusLine());
    }

    @Then("User calls Fulfillment service to fulfill the order")
    public void userCallsFulfillmentServiceToFulfillTheOrder() {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
        FulfillmentRequest = RestAssured.given();
        fulfillmentResponse=FulfillmentRequest.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                //    .body(OrderProvisioingResponse.toString())
                .post("/v1alpha1/orders/"+provisioningOrderID+":fulfillment")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("status line of Fulfillment response" +fulfillmentResponse.statusLine());
    }

    @Then("verify status of order updated to FULFILLED")
    public void verifyStatusOfOrderUpdatedToFULFILLED() {
        fullfillmentStatus = fulfillmentResponse.jsonPath().get("status");
        System.out.println("Status of order through Fulfillment service: "+fullfillmentStatus);

        //Get orderid
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
        getOrderIDRequest=RestAssured.given();
        getOrderIDResponse=getOrderIDRequest.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .get("/v1alpha1/orders/"+provisioningOrderID)
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("Status line of GetOrderID response "+getOrderIDResponse.statusLine());
        System.out.println("Status of order: "+getOrderIDResponse.jsonPath().get("status"));

    }
}
