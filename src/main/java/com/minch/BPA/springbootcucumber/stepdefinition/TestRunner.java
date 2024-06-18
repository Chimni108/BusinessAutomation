package com.minch.BPA.springbootcucumber.stepdefinition;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/main/java/features"},
        //classpath:features
               extraGlue={"com.minch.BPA.config"},
            //   glue = {"stepdefinition"},
            //   plugin = {"pretty","json:target/json-report/cucumber.json","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        //plugin={"pretty","html:target/cucumber-reports"},
           //   plugin = {"pretty","json:target/json-report/cucumber.json",
            //              "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","html:results/html/cucumber.html"},
               plugin={"pretty","html:results/html/cucumber.html"},

                monochrome = true )
public class TestRunner {
}
