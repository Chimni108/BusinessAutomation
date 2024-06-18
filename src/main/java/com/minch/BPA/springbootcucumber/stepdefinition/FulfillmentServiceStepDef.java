package com.minch.BPA.springbootcucumber.stepdefinition;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import com.minch.BPA.springbootcucumber.stepdefinition.CreateNewOrderService;
import io.restassured.specification.RequestSpecification;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import org.json.JSONException;

public class FulfillmentServiceStepDef {
    public static String tokenGenerated;
    RequestSpecification request;
    private Response response;
    //public static String fulfillmentOrderId;
    public String updatedStatusOfOrderId;

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

    @Given("User sets endpoint with orderId to update status of order")
    public void userSetsEndpointWithOrderIdToUpdateStatusOfOrder() {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
        System.out.println("base url set for fulfillmentservice");
        System.out.println("updating status of orderID: "+CreateNewOrderService.orderID);
      //  fulfillmentOrderId = CreateNewOrderService.orderID;
    }

    @When("status of order should be {string}")
    public void statusOfOrderShouldBe(String previousstatus) throws JSONException {
        System.out.println("current status of orderID " +CreateNewOrderService.NewOrderIdStatus);
        if((CreateNewOrderService.NewOrderIdStatus).equals(previousstatus))
        {
            System.out.println("we are updating correct status of orderID: "+previousstatus);
        }
        else
            System.out.println("if condition is not validated");
    }

    @Then("service is updating status of orderId to {string}")
    public void serviceIsUpdatingStatusOfOrderIdTo(String Status) {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject value = factory.createObjectBuilder()
                .add("sections",factory.createArrayBuilder()
                        .add(factory.createObjectBuilder()
                                .add("orderSectionId","eecde318-d7c0-4814-bdad-6c24fa39981c")
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
                                                .add("sku","SMS.NEW-PRICELIST-MO-INBOUND-LEGACY.NOVA")
                                                .add("status","NEW")
                                                .add("orderLineId","dc85134e-c7d0-4ab7-af78-fa2859cdc7a0")))
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
                        .add("registeredName","minch Denmark AB")
                        .add("sellerId","b9b05e88-feb4-48ea-bff7-d8de01a5101a"))
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
                        .add("buyerId","9c761167-1e6a-42c0-90ba-8756d8a9b1a4"))
                .add("commercial",factory.createObjectBuilder()
                        .add("paymentCurrency","SEK")
                        .add("contractTerm","FIXED")
                        .add("billingOption","PREPAY")
                        .add("billingCurrency","SEK")
                        .add("billingAccountId","11b61bc6c9d54ad4911e224ce521b48c"))
                .add("displayName","CreateNewOrder")
                .add("approvalStatus","UNDECIDED")
                .add("status", Status)
                .add("id","e65c3f73-947b-4fc7-b907-d87e791644bb").build();

        System.out.println("My JSON object request body" +value);
        request = RestAssured.given();
        response=request.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(value.toString())
                //   .pathParam("orderId","e65c3f73-947b-4fc7-b907-d87e791644bb")
                .patch("/v1alpha1/orders/"+CreateNewOrderService.orderID)
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("status line" +response.statusLine());
      //  System.out.println(response.jsonPath().prettify());
        updatedStatusOfOrderId=response.jsonPath().get("status");
        System.out.println("updatedStatusOfOrderId from service response: " +updatedStatusOfOrderId);
        if(updatedStatusOfOrderId.equals(Status))
        {
            System.out.println("Status of orderId is updated to:  "+Status);
        }
        else
            System.out.println("if condition is not validated");
    }
}
