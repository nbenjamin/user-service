package com.winter.core.userservice.adapter.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.winter.core.userservice.adapter.repository.jpa.UserRepository;
import com.winter.core.userservice.domain.CoreException;
import com.winter.core.userservice.domain.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController subject;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getAllUsers_returnAllUsers_successfully(){
        List<User> users = Collections.singletonList(new User("Ryan", "Adam", "ryanAdam",
                "abc123"));
        when(userRepository.findAll()).thenReturn(users);
        List<User> expected = subject.getAllUsers();
        assertThat(expected.get(0), is(equalTo(users.get(0))));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getAllUsers_returnEmptyUser_successfully(){
        List<User> users = Collections.emptyList();
        when(userRepository.findAll()).thenReturn(users);
        List<User> expected = subject.getAllUsers();
        assertThat(expected.size(), is(equalTo(0)));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getUser_byValidId_returnUser(){
        User user = new User( "Ryan", "Adam", "ryanAdam",
                "abc123");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User expected = subject.getUser(1L);
        assertThat(expected.getFirstName(), is(equalTo("Ryan")));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void getUser_byInValidId_returnException(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        thrown.expect(CoreException.class);
        thrown.expectMessage("User not found");
        subject.getUser(1L);
    }

    @Test
    public void getUserByFirstAndLastName_byNames_returnUser(){
        User user = new User("Ryan", "Adam", "ryanAdam",
                "abc123");
        when(userRepository.findByFirstNameAndLastName("Ryan", "Adam")).thenReturn(Optional.of
                (user));
        User expected = subject.getUserByFirstAndLastName("Ryan", "Adam");
        assertThat(expected.getFirstName(), is(equalTo("Ryan")));
        assertThat(expected.getLastName(), is(equalTo("Adam")));
        verify(userRepository, times(1)).findByFirstNameAndLastName("Ryan", "Adam");
    }

    @Test
    public void getUserByFirstAndLastName_byInValidNames_returnException(){
        when(userRepository.findByFirstNameAndLastName("Tom", "Jerry")).thenReturn(Optional.empty());
        thrown.expect(CoreException.class);
        thrown.expectMessage("Unable to find user with firstNameTom lastName Jerry");
        subject.getUserByFirstAndLastName("Tom", "Jerry");
    }

}