package com.winter.core.userservice;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.winter.core.userservice.app.UserServiceApplication;
import com.winter.core.userservice.domain.Group;

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

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment
        .RANDOM_PORT)
@ContextConfiguration
public class GroupApiSteps {

    private static final String HOST = "http://localhost:";

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<Group> groupResponseEntity;

    @When("^client POST a request /core/users/(\\d+)/groups with groupName \"([^\"]*)\"$")
    public void client_POST_a_request_core_users_groups_with_groupName(int userId, String
            groupName) throws Throwable {
        Group group = new Group();
        group.setGroupName(groupName);
        groupResponseEntity = restTemplate.postForEntity(getUriComponentsBuilder(userId)
                .pathSegment("groups").build().toUriString(),group, Group.class);
    }

    @Then("^group endpoint return httpStatus \"([^\"]*)\" in response$")
    public void group_endpoint_return_httpStatus_in_response(String httpStatus) throws Throwable {
        assertThat(groupResponseEntity.getStatusCode(), is(equalTo(HttpStatus.valueOf(httpStatus))));
    }

    private UriComponentsBuilder getUriComponentsBuilder(Integer userId) throws Exception{
        return UriComponentsBuilder.fromUri(new URI(HOST+port)).pathSegment("/core/users")
                .pathSegment(userId.toString());
    }
}
