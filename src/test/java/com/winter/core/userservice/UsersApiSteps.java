package com.winter.core.userservice;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.winter.core.userservice.app.UserServiceApplication;
import com.winter.core.userservice.domain.User;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest
        .WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class UsersApiSteps {

    @Value("${local.server.port}")
    private int port;
    private String hostName = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    private  ResponseEntity<User> responseEntity;

    @Given("^application is up and running$")
    public void application_is_up_and_running() throws Throwable {

    }

    @When("^client POST a request /core/users with the following information$")
    public void client_POST_a_request_core_users_with_the_following_information(DataTable dataTable)
            throws Throwable {
        User user = dataTable.asList(User.class).get(0);
        String url = getUriComponentsBuilder().build().toUriString();
        responseEntity =  restTemplate.postForEntity(url, user,User.class);

    }

    @Then("^return httpStatus \"([^\"]*)\" in response$")
    public void return_httpStatus_in_response(String httpStatus) throws Throwable {
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.valueOf(httpStatus))));
    }

    @Then("^makes a call to get User by \"([^\"]*)\" and \"([^\"]*)\"$")
    public void makes_a_call_to_get_User_by_and(String firstName, String lastName) throws
            Throwable {
        String url = getUriComponentsBuilder().pathSegment(firstName, lastName).build().toUriString();
        responseEntity = restTemplate.getForEntity(url, User.class);
    }

    @Then("^validate the user response with the below User details$")
    public void validate_the_user_response_with_the_below_User_details(DataTable userDataTable)
            throws Throwable {
        User user = userDataTable.asList(User.class).get(0);
    }

    // Scenario Outline: view existing user

    @When("^client GET a request user with resource url /core/users/\"([^\"]*)\"/\"([^\"]*)\"$")
    public void client_GET_a_request_user_with_resource_url_core_users(String firstName, String
            lastName) throws Throwable {

        responseEntity = restTemplate.getForEntity(getUriComponentsBuilder().pathSegment
                (firstName).pathSegment(lastName).build().toUriString(),User.class);
    }

    @Then("^return User with httpStatus \"([^\"]*)\"$")
    public void return_User_with_httpStatus(String httpStatus) throws Throwable {
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.valueOf(httpStatus))));

    }

    @Then("^verify the username \"([^\"]*)\"$")
    public void verify_the_username(String userName) throws Throwable {
        User user = responseEntity.getBody();
        if(null != user) {
            assertThat(user.getUserName(), is(equalTo(userName)));
        } else {
            assertThat(user, is(equalTo(null)));
        }

    }

    private UriComponentsBuilder getUriComponentsBuilder() throws Exception {
        return UriComponentsBuilder.fromUri(new URI(hostName+ port)).pathSegment
                ("/core/users");
    }
}

