package com.winter.core.userservice.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "GROUPS")
public class Group implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_ID")
    private Long groupId;
    private String groupName;
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            orphanRemoval = true)
    @JsonBackReference
    private List<UserGroup> userGroups = new ArrayList();

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public void addUser(User user) {
        UserGroup userGroup = new UserGroup(user, this);
        if(userGroups == null ){
            userGroups = new ArrayList();
        }
        userGroups.add(userGroup);
        user.getUserGroups().add(userGroup);
    }

    public void removeUser(User user) {
        this.userGroups.remove(user);
    }

    @Transient
    private List<User> users = new ArrayList<>();

}
