package com.github.gather.entity;


import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "user_group")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_table_id")
    private GroupTable group;

    @ManyToMany
    @JoinTable(name = "user_group_bookmarked_users", joinColumns = @JoinColumn(name = "user_group_id"), inverseJoinColumns = @JoinColumn(name = "bookmarked_user_id"))
    private List<User> bookmarkedUsers;

    @ManyToMany
    private List<User> users = new ArrayList<>();

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GroupTable getGroup() {
        return group;
    }

    public void setGroup(GroupTable group) {
        this.group = group;
    }

    public List<User> getBookmarkedUsers() {
        return bookmarkedUsers;
    }

    public void setBookmarkedUsers(List<User> bookmarkedUsers) {
        this.bookmarkedUsers = bookmarkedUsers;
    }

    public UserGroup() {
    }

    public UserGroup(Long groupId, User user, GroupTable group, List<User> bookmarkedUsers) {
        this.groupId = groupId;
        this.user = user;
        this.group = group;
        this.bookmarkedUsers = bookmarkedUsers;
    }

    public static UserGroup from(UserGroupTable userGroupTable) {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(userGroupTable.getGroupId());
        return userGroup;
    }
}