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


    @Query("SELECT (CASE WHEN COUNT(UG.user.userId) > 0 THEN true ELSE false END) FROM UserGroup" +
            " UG INNER JOIN User U" +
            " ON UG.user.userId = U.userId INNER JOIN Group G ON UG.group.groupId = G.groupId" +
            " WHERE U.userId = ?1 AND G.groupName= ?2")
    boolean isGroupNameExistsForUser(Long userId, String groupName);

}
