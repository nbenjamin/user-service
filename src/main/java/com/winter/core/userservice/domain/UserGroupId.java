package com.winter.core.userservice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserGroupId implements Serializable {

    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "GROUP_ID")
    private Long groupId;
}
