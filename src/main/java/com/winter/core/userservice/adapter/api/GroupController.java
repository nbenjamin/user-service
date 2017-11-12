package com.winter.core.userservice.adapter.api;


import com.winter.core.userservice.adapter.repository.jpa.GroupRepository;
import com.winter.core.userservice.adapter.repository.jpa.UserGroupRepository;
import com.winter.core.userservice.adapter.repository.jpa.UserRepository;
import com.winter.core.userservice.domain.CoreException;
import com.winter.core.userservice.domain.Group;
import com.winter.core.userservice.domain.User;
import com.winter.core.userservice.domain.UserGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/core")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;

    @PostMapping("/users/{id}/groups")
    public ResponseEntity<Group> createGroup(@RequestBody Group group, @PathVariable("id")
            Long userId) {
        User user = this.userRepository.findById(userId).get();
        Group groupPersisted = groupRepository.save(group);
        groupPersisted.addUser(user);
        return Optional.ofNullable(groupRepository.save(groupPersisted)).map(g ->new
                ResponseEntity<Group>
                (g, HttpStatus.CREATED) ).orElseThrow(() -> new CoreException("Unable to create " +
                "group now, please try again"));
    }

    @GetMapping("/groups/{id}")
    public List<Group> getGroupsById(@PathVariable("id")
                                             Long userId) {
        List<UserGroup> userGroups = userGroupRepository.findAllByUser_UserId(userId);
        List<Group> groups = userGroups.stream().map(userGroup -> userGroup.getGroup()).collect(Collectors.toList());
        return groups;
    }

    @DeleteMapping("/users/{userId}/groups/{groupId}")
    public ResponseEntity<Void> getGroupByName(@PathVariable("userId") Long userId, @PathVariable
            ("groupId") Long
            groupId) {
        userGroupRepository.deleteUserGroupByGroup_GroupIdAndUser_UserId(userId, groupId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/groups/{groupId}")
    public void updateGroupWithUsers(@PathVariable("groupId") Long userId, @PathVariable
            ("groupId") Long groupId, @RequestParam
            ("userIds") List<Long> userIds) {
        UserGroup userGroup = new UserGroup();
        Group group = groupRepository.findById(groupId).get();
        group.setGroupId(groupId);
        userIds.stream().map(aLong -> userRepository.findById(aLong)).forEach(u -> {
            group.addUser(u.get());
        });
        userGroup.setGroup(group);
        groupRepository.save(group);

    }
}
