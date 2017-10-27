package com.winter.core.userservice.adapter.api;


import com.winter.core.userservice.adapter.repository.GroupRepository;
import com.winter.core.userservice.adapter.repository.UserRepository;
import com.winter.core.userservice.domain.CoreException;
import com.winter.core.userservice.domain.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/core/users")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{id}/groups")
    public ResponseEntity<Group> createGroup(@RequestBody Group group, @PathVariable("id")
            Long userId) {
        group.setUser(this.userRepository.findById(userId).get());
        this.groupRepository.save(group);
        return new ResponseEntity<Group>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/groups")
    public List<Group> createGroup(@PathVariable("id")
            Long userId) {
        return StreamSupport.stream(this.groupRepository.findByUserUserId(userId).spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/groups")
    public Group createGroup(@RequestParam("name") String groupName) {
        return Optional.ofNullable(this.groupRepository.findByGroupName(groupName).get()).orElseThrow(
                () -> new CoreException("Unable to find group information by name"));
    }
}
