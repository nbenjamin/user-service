package com.winter.core.userservice.adapter.repository.jpa;

import com.winter.core.userservice.domain.Group;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 *
 */
public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByGroupName(String groupName);

}
