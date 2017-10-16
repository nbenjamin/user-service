package com.winter.core.userservice;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber", "json:target/cucumber-json-report" +
        ".json"}, features = "src/test/resources/features/")
public class AcceptanceTest {
}
