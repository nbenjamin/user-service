package com.winter.core.userservice.adapter.repository.jpa;

import com.winter.core.userservice.domain.Group;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import javax.transaction.Transactional;

/**
 *
 */
public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByGroupName(String groupName);

    @Modifying
    @Transactional
    @Query("delete from UserGroup u where user.userId = ?1 and group.groupId =?2")
    void deleteUserFromUser(Long userId, Long groupId);
}
