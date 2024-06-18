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

public class OrderingTestScenarioStepDef {
    public static String tokenGenerated;
    private Response FirstScenarioResponse;
    private Response SecondScenarioResponse;
    private Response ThirdScenarioResponse;
    private Response FourthScenarioResponse;
    RequestSpecification FirstScenarioRequest;
    RequestSpecification SecondScenarioRequest;
    RequestSpecification ThirdScenarioRequest;
    RequestSpecification FourthScenarioRequest;

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
    @Given("User set createNewOrder endpoint for FirstScenario")
    public void userSetCreateNewOrderEndpointForFirstScenario() {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
      //  System.out.println("base url set");
    }

    @When("no configurations is provided in body")
    public void noSigningAndNoConfigurationsIsProvidedInBody() {
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject value = factory.createObjectBuilder()
                .add("sections",factory.createArrayBuilder()
                        .add(factory.createObjectBuilder()
                                .addNull("orderSectionId")
                                .add("orderLines",factory.createArrayBuilder()
                                        .add(factory.createObjectBuilder()
                                                .add("configs",factory.createArrayBuilder()
                                                        .add(factory.createObjectBuilder()
                                                        //        .add("config",factory.createObjectBuilder())
                                                                .add("quantity",1)))
                                                .addNull("charges")
                                                .add("charges",factory.createArrayBuilder())
                                                .add("sku","SMS.NEW-PRICELIST-MO-INBOUND-LEGACY.NOVA")
                                                .add("status","NEW")))
                                .add("basePriceBook",factory.createObjectBuilder()
                                        .add("solutionUri","https://smart-solution.eu1tst.bpa.unauth.int.staging.minch.com/examples/solution.sms-mario-edit-existing-products.json")
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

     //   System.out.println("My JSON object request body" +value);
        FirstScenarioRequest = RestAssured.given();
        FirstScenarioResponse=FirstScenarioRequest.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(value.toString())
                .post("/v1alpha1/orders")
                .then().assertThat().statusCode(400).extract().response();
        System.out.println("status line" +FirstScenarioResponse.statusLine());
        //System.out.println(FirstScenarioResponse.jsonPath().prettify());

    }

    @Then("OrderId should not be created with orderID and OrderStatus IN_DRAFT")
    public void orderidShouldBeCreatedWithOrderIDAndOrderStatusIN_DRAFT() {
        System.out.println("order ID created for First Scenario: "+FirstScenarioResponse.jsonPath().get("id"));
    }

    @Given("User set createNewOrder endpoint for SecondScenario")
    public void userSetCreateNewOrderEndpointForSecondScenario() {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
        //System.out.println("base url set");
    }

    @When("required Configurations is provided in body")
    public void noSigningAndRequiredConfigurationsIsProvidedInBody() {
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
                                                                        .add("projectId","1ee8731d-b5f8-448e-b4ef-b909cb688773")
                                                                        .add("siteGroupIds",factory.createArrayBuilder()
                                                                                .add(23)))
                                                                .add("quantity",1)
                                                                .add("id","2643e2f8-723d-4871-a3ad-070153d6dc11")))
                                                //.addNull("charges")
                                                //.add("charges",factory.createArrayBuilder())
                                                .add("sku","SMS.1WAY-ROW.NOVA")
                                                .add("status","NEW")))
                                .add("basePriceBook",factory.createObjectBuilder()
                                        .add("solutionUri","https://smart-solution.eu1tst.bpa.unauth.int.staging.minch.com/examples/solution.sms-automated-products.nova.json")
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

        //System.out.println("My JSON object request body" +value);

        SecondScenarioRequest = RestAssured.given();
        SecondScenarioResponse=SecondScenarioRequest.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(value.toString())
                .post("/v1alpha1/orders")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("status line" +SecondScenarioResponse.statusLine());
        //System.out.println(SecondScenarioResponse.jsonPath().prettify());
    }

    @Then("OrderId should be created with orderID and OrderStatus IN_DRAFT for SecondScenario")
    public void orderidShouldBeCreatedWithOrderIDAndOrderStatusIN_DRAFTForSecondScenario() {
        System.out.println("order ID created for Second Scenario: "+SecondScenarioResponse.jsonPath().get("id"));
    }

    @Given("User set createNewOrder endpoint for FourthScenario")
    public void userSetCreateNewOrderEndpointForFourthScenario() {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
        //System.out.println("base url set");
    }

    @When("User do not provides productId and details in request body")
    public void userDoNotProvidesProductIdAndDetailsInRequestBody() {
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
                                                                        .add("projectId","1ee8731d-b5f8-448e-b4ef-b909cb688773")
                                                                        .add("siteGroupIds",factory.createArrayBuilder()
                                                                                .add(23)))
                                                                .add("quantity",1)
                                                                .add("id","38487yf906")))
                                                //.addNull("charges")
                                                .add("charges",factory.createArrayBuilder())
                                                .add("sku","")
                                                .add("status","NEW")))
                                .add("basePriceBook",factory.createObjectBuilder()
                                        .add("solutionUri","https://smart-solution.eu1tst.bpa.unauth.int.staging.minch.com/examples/solution.sms-test-service.nova.json")
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

        FourthScenarioRequest = RestAssured.given();
        FourthScenarioResponse=FourthScenarioRequest.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(value.toString())
                .post("/v1alpha1/orders")
                .then().assertThat().statusCode(400).extract().response();
        System.out.println("status line" +FourthScenarioResponse.statusLine());
        //System.out.println(FourthScenarioResponse.jsonPath().prettify());
    }

    @Then("orderID should not be created")
    public void orderidShouldNotBeCreated() {
        System.out.println("order not created if product not provided: "+FourthScenarioResponse.getStatusCode());
    }
}
