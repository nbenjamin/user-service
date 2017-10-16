package com.winter.core.userservice;

import com.winter.core.userservice.app.UserServiceApplication;
import com.winter.core.userservice.domain.User;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest
        .WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class UsersApiSteps {

    @Given("^application is up and running$")
    public void application_is_up_and_running() throws Throwable {

    }

    @When("^client POST a request /core/user with the following information$")
    public void client_POST_a_request_core_user_with_the_following_information(List<User> users)
            throws Throwable {
       users.forEach(System.out::println);

    }
    @Then("^return httpstatus <httpstatus>$")
    public void return_httpstatus_httpstatus() throws Throwable {

    }


}

