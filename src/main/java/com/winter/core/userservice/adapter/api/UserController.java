package com.winter.core.userservice.adapter.api;

import com.winter.core.userservice.adapter.repository.jpa.UserRepository;
import com.winter.core.userservice.domain.CoreException;
import com.winter.core.userservice.domain.User;
import com.winter.core.userservice.domain.UserGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 */
@RestController
@RequestMapping("/core/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return users;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long userId) {
        User user = Optional.ofNullable(userRepository.findById(userId)).get().orElseThrow(() -> new
                CoreException("User not found"));
        List<UserGroup> userGroups = user.getUserGroups();
        userGroups.parallelStream().map(userGroup -> userGroup.getGroup()).forEach(user.getGroups():: add);
        return user;
    }

    @GetMapping("/{firstName}/{lastName}")
    public User getUserByFirstAndLastName(@PathVariable("firstName") String firstName,
                                          @PathVariable("lastName") String lastName) {
        return Optional.ofNullable(userRepository.findByFirstNameAndLastName(firstName, lastName))
                .get().orElseThrow(() -> new CoreException("Unable to find user with firstName"+
                firstName + " lastName "+ lastName));
    }

    @GetMapping("/login")
    public User getUserByUserNameAndPassword(@RequestParam("userName") String userName,
                                          @RequestParam("password") String password) {
        return Optional.ofNullable(userRepository.findByUserNameAndPassword(userName, password))
                .get().orElseThrow(() -> new CoreException("Unable to find user with userName"+
                        userName + " password "+ password));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
       return Optional.ofNullable(userRepository.save(user)).map( t -> new ResponseEntity<User>
                (t, HttpStatus.CREATED)).orElseThrow( () -> new CoreException("Unable to insert " +
                "new User, please try again"));
    }
}
