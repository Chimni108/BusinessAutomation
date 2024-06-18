package com.minch.BPA.springbootcucumber.stepdefinition;

import com.mongodb.util.JSON;
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
import org.json.JSONObject;


//import javax.json.Json;
//import javax.json.JsonBuilderFactory;
//import javax.json.JsonObject;


public class CreateNewOrderService {
    private Response response;
    String JsonString_resonse;
    public static String tokenGenerated;
    RequestSpecification request;
    static int statcode;
    JSONObject jsondata;

    public static String orderID;
    public static String orderSectionId;
    public static String product;
    public static String notes;
    public static String sku;
    public static String orderLineId;
    public static String sellerId;
    public static String sellerregisteredName;
    public static String novaShortName;
    public static String buyerId;
    public static String NewOrderIdStatus;

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

    @Given("user set CreateNewOrder endpoint")
    public void userSetCreateNewOrderEndpoint() {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
        System.out.println("base url set");
    }

    @When("user send CreateNewOrder request body")
    public void userSendCreateNewOrderRequestBody() throws JSONException {
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
                                                                        .add("campaignId","campaignId")
                                                                        .add("orderNumber","133423")
                                                                        .add("shortCode","sra")
                                                                        .add("tariff","Standard (5555)"))
                                                                .add("quantity",1)
                                                                .add("id","96117257-90b4-40cc-a690-e0e0074035fc")))
                                                //.addNull("charges")
                                                .add("charges",factory.createArrayBuilder())
                                                .add("sku","SMS.ADD-VERIZON-CAMPAIGN-ID.NOVA")
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

        request = RestAssured.given();
        response=request.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .body(value.toString())
                .post("/v1alpha1/orders")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println("status line" +response.statusLine());
      //  System.out.println(response.jsonPath().prettify());

        orderID=response.jsonPath().get("id");
        orderSectionId=response.jsonPath().get("orderSectionId");
        product= response.jsonPath().get("product");
        notes= response.jsonPath().get("notes");
        sku=response.jsonPath().get("sku");
        orderLineId=response.jsonPath().get("orderLineId");
        sellerId=response.jsonPath().get("sellerId");
        sellerregisteredName=response.jsonPath().get("registeredName");
        novaShortName=response.jsonPath().get("novaShortName");
        buyerId=response.jsonPath().get("buyerId");
        NewOrderIdStatus=response.jsonPath().get("status");
        System.out.println("orderID : " +orderID);
    }

    @Then("validate success response and orderId")
    public void validateSuccessResponseAndOrderId() {

       System.out.println("Success");
    }
}
