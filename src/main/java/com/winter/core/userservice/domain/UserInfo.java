package com.winter.core.userservice.domain;

import java.util.List;

import lombok.Data;

@Data
public class UserInfo {

    private User user;
    private List<Group> groups;
}
