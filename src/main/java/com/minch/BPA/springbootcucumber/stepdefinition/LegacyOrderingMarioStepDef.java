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
import org.json.JSONException;
import jakarta.json.JsonObjectBuilder;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class LegacyOrderingMarioStepDef {
    public static String tokenGenerated;
    private Response response;
    private Response minchAccountResponse;
    private Response NovaAccountResponse;
    private Response OrderProvisioingResponse;
    private Response fulfillmentResponse;
    RequestSpecification request;
    RequestSpecification createOrderRequest;
    RequestSpecification UpdateOrderRequest;
    RequestSpecification FulfillmentRequest;
    public static String minchAccountID;
    public static String novaAccountID;
    public static String  provisioningOrderID;
    public static String  fullfillmentStatus;
    private Response updateOrderProvisioingResponse;
    private static String orderStatus;

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
/*
    @Given("user set CreateNewOrder endpoint to create order for mario solutions- product")
    public void userSetCreateNewOrderEndpointToCreateOrderForMarioSolutionsProduct() {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
        System.out.println("base url set");
    }

    @When("user provides product as {string}")
    public void userProvidesProductAs(String marioproduct) {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject value = factory.createObjectBuilder()
                .add("sections",factory.createArrayBuilder()
                        .add(factory.createObjectBuilder()
                                .addNull("orderSectionId")
                                .add("orderLines",factory.createArrayBuilder()
                                        .add(factory.createObjectBuilder()
                                                .add("configs",factory.createArrayBuilder()
                                                        .add(factory.createObjectBuilder()
                                                                .add("config",factory.createObjectBuilder()
                                                                        .add("notes","sms - mo inbound")
                                                                        .add("priceStability",1)
                                                                        .add("product","Argentina Free To End User Short Code 2-Way MO"))
                                                                .add("quantity",1)))
                                                //.addNull("charges")
                                                .add("charges",factory.createArrayBuilder())
                                                .add("sku",marioproduct)
                                                .add("status","NEW")))
                                .add("basePriceBook",factory.createObjectBuilder()
                                        .add("solutionUri","https://smart-solution.eu1tst.bpa.unauth.int.staging.minch.com/v1alpha1/examples/solution.sms-legacy-ordering-aka-mario.nova.json")
                                        .add("version","1970-01-01")
                                        .add("priceBookId",""))))
                .add("seller",factory.createObjectBuilder()
                        .add("contacts",factory.createObjectBuilder()
                                .add("support",factory.createObjectBuilder()
                                        .add("displayName","TBD")
                                        .add("email","TBD@example.minch.com"))
                                .add("primary",factory.createObjectBuilder()
                                        .add("displayName","TBD")
                                        .add("email","TBD@example.minch.com"))
                                .add("finance",factory.createObjectBuilder()
                                        .add("displayName","TBD")
                                        .add("email","TBD@example.minch.com")))
                        .add("address",factory.createObjectBuilder()
                                .add("country","DK"))
                        .add("novaShortName","DK AB")
                        .add("registeredName","minch Denmark AB"))
                .add("buyer",factory.createObjectBuilder()
                        .add("website","https://www.microsoft.com")
                        .add("taxId","89898989")
                        .add("existingCustomer",false)
                        .add("registrationNr","546789625")
                        .add("corporateName","MICROSOFT CORPORATION")
                        .add("contacts",factory.createObjectBuilder()
                                .add("primary",factory.createObjectBuilder()
                                        .add("phone"," ")
                                        .add("displayName","Trambak Swami")
                                        .add("email","v-trswam@microsoft.cominvalidated")
                                        .add("contactId","0037R00002tWfrnQAC"))
                                .add("finance",factory.createObjectBuilder()
                                        .add("phone","")
                                        .add("displayName","Trambak Swami")
                                        .add("email","v-trswam@microsoft.cominvalidated")
                                        .add("contactId","0037R00002tWfrnQAC")))
                        .add("address",factory.createObjectBuilder()
                                .add("postalCode","980528300")
                                .add("country","US")
                                .add("stateOrProvince","WA")
                                .add("city","Redmond")
                                .add("line1","1 MICROSOFT WAY"))
                        .add("crmAccountId","0017R00002W5pE6QAJ")
                        .add("buyerId",""))
                .add("commercial",factory.createObjectBuilder()
                        .add("paymentCurrency","SEK")
                        .add("contractTerm","FIXED")
                        .add("billingOption","PREPAY")
                        .add("billingCurrency","SEK")
                        .add("billingAccountId","11b61bc6c9d54ad4911e224ce521b48c"))
                .add("displayName","CreateNewOrder")
                .add("approvalStatus","UNDECIDED")
                .add("status", "IN_DRAFT").build();

        System.out.println("My JSON object request body" +value);

        request = RestAssured.given();
        response=request.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(value.toString())
                .post("/v1alpha1/orders")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("status line" +response.statusLine());
        System.out.println(response.jsonPath().prettify());
    }

    @Then("user should get orderID created for mario product")
    public void userShouldGetOrderIDCreatedForMarioProduct() {
        System.out.println("orderId created for mario solutions: " +response.jsonPath().get("id"));
    }*/

    @Given("user set endpoint for creating new minch account")
    public void userSetEndpointForCreatingNewminchAccount() {
        RestAssured.baseURI="https://provisioning.eu1tst.bpa.staging.minch.com:443";
        //System.out.println("base url set");
    }

    @When("user provides request body as {string}")
    public void userProvidesRequestBodyAs(String displayName) {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
       JsonObject value = factory.createObjectBuilder().add("displayName",displayName).build();

        request = RestAssured.given();
        minchAccountResponse=request.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(value.toString())
                .post("/v1alpha1/accounts")
                .then().assertThat().statusCode(200).extract().response();
    }

    @Then("user should get minchAccountId as response from CreateNewminchAccount API")
    public void userShouldGetminchAccountIdAsResponseFromCreateNewminchAccountAPI() {
        minchAccountID = minchAccountResponse.jsonPath().get("minchAccountId");
        System.out.println("minch accountId: " +minchAccountID);
    }


    @Given("user set endpoint for create new nova sms account")
    public void userSetEndpointForCreateNewNovaSmsAccount() {
        RestAssured.baseURI="https://provisioning.eu1tst.bpa.staging.minch.com:443";
    }

    @When("user provides minchAccountID as input")
    public void userProvidesminchAccountIDAsInput() {
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
    }

    @Then("user should get NovaAccountId created from CreateNewNovaSMSAccount")
    public void userShouldGetNovaAccountIdCreatedFromCreateNewNovaSMSAccount() {
        novaAccountID=NovaAccountResponse.jsonPath().get("novaAccountId");
        System.out.println("nova account Id "+novaAccountID);
    }

    @Given("user sets endpoint - CreateNewOrder")
    public void userSetsEndpointCreateNewOrder() {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
    }

    @When("product provided should be {string}")
    public void productProvidedShouldBe(String marioproduct) {

        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject value = factory.createObjectBuilder()
                .add("sections",factory.createArrayBuilder()
                        .add(factory.createObjectBuilder()
                                .addNull("orderSectionId")
                                .add("orderLines",factory.createArrayBuilder()
                                        .add(factory.createObjectBuilder()
                                                .add("configs",factory.createArrayBuilder()
                                                        .add(factory.createObjectBuilder()
                                                                .add("config",factory.createObjectBuilder()
                                                                        .add("clientContact","9876543210")
                                                                        .add("clientTestingOfMoNeededBeforeMigration","no")
                                                                        .add("deliverMoMessageSToAccount","Newly Created Account within this Order")
                                                                        .add("doASingleNumberTestFirst","no")
                                                                        .add("listOfNumbersToMigrate","9898989898")
                                                                        .add("notes","yhpl")
                                                                        .add("scheduledMigrationSessionNeeded","no"))
                                                                .add("quantity",1)
                                                                .add("id","59cbd8db-dc84-443d-bff6-996cbe6e822a")))
                                                //.addNull("charges")
                                                .add("charges",factory.createArrayBuilder())
                                                .add("sku",marioproduct)
                                                .add("status","NEW")))
                                .add("basePriceBook",factory.createObjectBuilder()
                                        .add("solutionUri","https://smart-solution.eu1tst.bpa.unauth.int.staging.minch.com/examples/solution.sms-mario-everything-else.nova.json")
                                        .add("version","1970-01-01")
                                        .add("priceBookId",""))))
                .add("seller",factory.createObjectBuilder()
                        .add("contacts",factory.createObjectBuilder()
                                .add("support",factory.createObjectBuilder()
                                        .add("displayName","TBD")
                                        .add("email","TBD@example.minch.com"))
                                .add("primary",factory.createObjectBuilder()
                                        .add("displayName","TBD")
                                        .add("email","TBD@example.minch.com"))
                                .add("finance",factory.createObjectBuilder()
                                        .add("displayName","TBD")
                                        .add("email","TBD@example.minch.com")))
                        .add("address",factory.createObjectBuilder()
                                .add("country","DK"))
                        .add("novaShortName","DK AB")
                        .add("registeredName","minch Denmark AB"))
                .add("buyer",factory.createObjectBuilder()
                        .add("website","https://www.microsoft.com")
                        .add("taxId","89898989")
                        .add("existingCustomer",false)
                        .add("registrationNr","546789625")
                        .add("corporateName","MICROSOFT CORPORATION")
                        .add("contacts",factory.createObjectBuilder()
                                .add("primary",factory.createObjectBuilder()
                                        .add("phone"," ")
                                        .add("displayName","Trambak Swami")
                                        .add("email","v-trswam@microsoft.cominvalidated")
                                        .add("contactId","0037R00002tWfrnQAC"))
                                .add("finance",factory.createObjectBuilder()
                                        .add("phone","")
                                        .add("displayName","Trambak Swami")
                                        .add("email","v-trswam@microsoft.cominvalidated")
                                        .add("contactId","0037R00002tWfrnQAC")))
                        .add("address",factory.createObjectBuilder()
                                .add("postalCode","980528300")
                                .add("country","US")
                                .add("stateOrProvince","WA")
                                .add("city","Redmond")
                                .add("line1","1 MICROSOFT WAY"))
                        .add("crmAccountId","0017R00002W5pE6QAJ")
                        .add("buyerId",""))
                .add("commercial",factory.createObjectBuilder()
                        .add("paymentCurrency","SEK")
                        .add("contractTerm","FIXED")
                        .add("billingOption","PREPAY")
                        .add("billingCurrency","SEK")
                        .add("billingAccountId",novaAccountID))
                .add("displayName","CreateNewOrder")
                .add("approvalStatus","UNDECIDED")
                .add("status", "IN_DRAFT").build();

        System.out.println("My JSON object request body" +value);

        createOrderRequest = RestAssured.given();
        OrderProvisioingResponse=createOrderRequest.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(value.toString())
                .post("/v1alpha1/orders")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("status line of CreateNewOrder response" +OrderProvisioingResponse.statusLine());
       // System.out.println(OrderProvisioingResponse.jsonPath().prettify());

    }

    @Then("orderId should be created with status as IN_DRAFT")
    public void orderidShouldBeCreatedWithStatusAsIN_DRAFT() {
        provisioningOrderID=OrderProvisioingResponse.jsonPath().get("id");
        System.out.println("OrderId: "+provisioningOrderID);
        orderStatus=OrderProvisioingResponse.jsonPath().get("status");
        System.out.println("Order status: "+orderStatus);
    }

    @Given("user set endpoint to update status of OrderID to IN_FULFILLMENT")
    public void userSetEndpointToUpdateStatusOfOrderIDToIN_FULFILLMENT() {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
    }

    @Then("status of orderId is updated to IN_FULFILLMENT")
    public void statusOfOrderIdIsUpdatedTo() throws JSONException {
        UpdateOrderRequest = RestAssured.given();
        JSONObject jsonObjectResponse= new JSONObject(OrderProvisioingResponse.getBody().asString());
        jsonObjectResponse.put("status","IN_FULFILLMENT");
            System.out.println("req json object"+jsonObjectResponse.toString());

        updateOrderProvisioingResponse=UpdateOrderRequest.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(jsonObjectResponse.toString())
                .patch("/v1alpha1/orders/"+provisioningOrderID).then().extract().response();
            //    .then().assertThat().statusCode(200).extract().response();
        System.out.println("status line of PATCH response" +updateOrderProvisioingResponse.statusLine());
        System.out.println("updateOrderProvisioingResponse response body " +updateOrderProvisioingResponse.getBody().toString());
        Assert.assertEquals(200,updateOrderProvisioingResponse.getStatusCode());
        System.out.println("Status of order is updated to IN_FULFILLMENT");

    }

    @Given("User sets Fulfillment service endpoint to fulfill the order providing orderID")
    public void userSetsFulfillmentServiceEndpointToFulfillTheOrderProvidingOrderID() {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
    }

    @Then("response with success code OK should be received")
    public void responseWithSuccessCodeOKShouldBeReceived() {
        FulfillmentRequest = RestAssured.given();
        fulfillmentResponse=FulfillmentRequest.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
            //    .body(OrderProvisioingResponse.toString())
                .post("/v1alpha1/orders/"+provisioningOrderID+":fulfillment")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("status line of Fulfillment response" +fulfillmentResponse.statusLine());

    }

    @Then("verify status of order should be FULFILLED")
    public void verifyStatusOfOrderShouldBe() throws InterruptedException {
      //  Thread.sleep(10);
        fullfillmentStatus = fulfillmentResponse.jsonPath().get("status");
        System.out.println("Status of order through Fulfillment service: "+fullfillmentStatus);
    }}
