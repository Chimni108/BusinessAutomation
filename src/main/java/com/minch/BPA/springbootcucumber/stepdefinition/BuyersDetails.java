package com.minch.BPA.springbootcucumber.stepdefinition;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

@Slf4j
public class BuyersDetails {
    private Response response;
    String JsonString_resonse;
    public static String tokenGenerated;
    RequestSpecification request;
    static int statcode;

    @Before
    public void generateOAuthtoken()
    {
        RestAssured.baseURI="https://public.token-service.common-auth.staging.minch.com";
        Response response1= RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .headers("Authorization","Basic YnBhLXByb3Zpc2lvbmluZy1zdGFnaW5nLXRlc3Q6cF84QkhtTjllYWI3N2p6YmNCUVN1eWMwR3g=")
                .formParam("grant_type","client_credentials")
                .post("https://public.token-service.common-auth.staging.minch.com/oauth2/token");
        //System.out.println(response1.jsonPath().prettify());
        String jsonString=response1.getBody().asString();
        tokenGenerated= JsonPath.from(jsonString).get("access_token");
      //  System.out.println("TokenGenerated: "+tokenGenerated);
    }

    @Given("user set api endpoint")
    public void userSetApiEndpoint() {
        RestAssured.baseURI="https://ordering.eu1tst.bpa.staging.minch.com:443";
        System.out.println("base url set");
    }

    @When("user send api request")
    public void userSendApiRequest() {
        System.out.println("I am inside when");
        //  RequestSpecification httpreq= (RequestSpecification) RestAssured.given()
        request=RestAssured.given();
       // System.out.println("Token1" +tokenGenerated);
        response= request.contentType("application/json")
                .header("Authorization","Bearer "+tokenGenerated)
                .get("/v1alpha1/buyers")
                .then().assertThat().statusCode(200).extract().response();
       // log.info("error faced: "+response);
        String StatusLine=response.getStatusLine();
        System.out.println("Status line" +StatusLine);
        statcode=response.getStatusCode();
        System.out.println("Status code: "+statcode);
        Assert.assertEquals(statcode,200);
      //  System.out.println("Testcase Passed");
      //  System.out.println(response.jsonPath().prettify());
    }

    @Then("validate success response")
    public void validateSuccessResponse() {
      //  System.out.println("I am inside THEN");
        Assert.assertEquals(statcode,200);
        System.out.println("Buyers details Test pass: "+statcode);
        response.then().assertThat().statusCode(200).extract().response();
        //System.out.println("test case pass");
    }
}
