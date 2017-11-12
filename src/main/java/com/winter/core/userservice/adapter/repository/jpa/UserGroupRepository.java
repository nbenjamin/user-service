package com.winter.core.userservice.adapter.repository.jpa;

import com.winter.core.userservice.domain.UserGroup;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import javax.transaction.Transactional;

/**
 *
 */
public interface UserGroupRepository extends CrudRepository<UserGroup, Long>{

    List<UserGroup> findAllByUser_UserId(Long userId);

    @Modifying
    @Transactional
    @Query("delete from UserGroup u where user.userId = ?1 and group.groupId =?2")
    void deleteUserGroupByGroup_GroupIdAndUser_UserId(Long userId, Long groupId);

}
