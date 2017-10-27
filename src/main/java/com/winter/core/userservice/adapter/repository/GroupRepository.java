package com.winter.core.userservice.adapter.repository;

import com.winter.core.userservice.domain.Group;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 *
 */
public interface GroupRepository extends CrudRepository<Group, Long> {

    Iterable<Group> findByUserUserId(Long userId);
    Optional<Group> findByGroupName(String groupName);

}
