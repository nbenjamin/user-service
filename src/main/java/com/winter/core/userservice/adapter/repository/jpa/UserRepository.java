package com.winter.core.userservice.adapter.repository.jpa;

import com.winter.core.userservice.domain.User;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<User> findByUserNameAndPassword(String userName, String password);
}
