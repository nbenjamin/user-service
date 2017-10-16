package com.winter.core.userservice.adapter.repository;

import com.winter.core.userservice.domain.User;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
}
