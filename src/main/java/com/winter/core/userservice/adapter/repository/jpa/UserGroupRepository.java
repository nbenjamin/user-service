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
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

    List<UserGroup> findAllByUser_UserId(Long userId);

    @Modifying
    @Transactional
    @Query("delete from UserGroup u where user.userId = ?1 and group.groupId =?2")
    void deleteUserGroupByGroup_GroupIdAndUser_UserId(Long userId, Long groupId);


/*    select CASE

    WHEN COUNT(c) >0THEN true ELSE false
    END FROM
    from User_group
    ug inner
    join user
    u on
    ug.user_id =
    u.user_id inner
    join groups
    g on
    ug.group_id =
    g.group_id where
    u.user_id=1*/

//    @Modifying
//    @Query()
//    boolean isGroupNameExistForUserId(Long userId, String groupName);

}
