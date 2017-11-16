package com.winter.core.userservice.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "UserGroup")
public class UserGroup implements Serializable{

    /*@EmbeddedId
    private UserGroupId id;
*/
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    public UserGroup(User user, Group group) {
       // id = new UserGroupId(user.getUserId(), group.getGroupId());
        this.user = user;
        this.group = group;
    }

}
