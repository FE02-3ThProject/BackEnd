package com.github.gather.entity;

import com.github.gather.entity.Role.GroupMemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "group_member")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_idx")
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupTable groupId;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private GroupMemberRole role;


/*    @Builder
    public GroupMember(Long memberId, User userId, GroupTable groupId, GroupMemberRole role) {
        this.memberId = memberId;
        this.userId = userId;
        this.groupId = groupId;
        this.role = role;
    }*/
    
    public GroupMember(User userId, GroupTable groupId, GroupMemberRole role) {
        this.userId = userId;
        this.groupId = groupId;
        this.role = role;
    }

}
