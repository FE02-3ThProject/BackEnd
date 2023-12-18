package com.github.gather.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_group_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGroupTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name")
    private String groupName;


    // 그룹에 참여한 사용자 목록
    @ManyToMany
    @JoinTable(
            name = "user_group_member",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members = new HashSet<>();


    // 북마크한 사용자 목록
    @ManyToMany
    @JoinTable(
            name = "user_group_bookmark",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> bookmarkedBy = new HashSet<>();
}
