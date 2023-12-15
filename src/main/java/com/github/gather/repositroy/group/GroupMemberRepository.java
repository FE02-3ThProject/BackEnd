package com.github.gather.repositroy.group;

import com.github.gather.entity.GroupMember;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember,Long> {

    @Query("SELECT gm FROM GroupMember gm WHERE gm.role = 'Leader' AND gm.groupId.groupId = :groupId")
    GroupMember findGroupMemberByRoleLeader(Long groupId);
    @Query("SELECT gm FROM GroupMember gm WHERE gm.groupId.groupId = :groupId")
    List<GroupMember> findGroupMemebersByGroupId(Long groupId);

    // groupId로 해당되는 모든 멤버 삭제
    @Transactional
    @Modifying
    @Query("DELETE FROM GroupMember gm WHERE gm.groupId.groupId = :groupId")
    void deleteGroupMembersByGroupId(Long groupId);

    int countByGroupId(GroupTable group);
    boolean existsByGroupIdAndUserId(GroupTable group, User user);

    Optional<GroupMember> findByUserIdAndGroupId(User user, GroupTable groupTable);
}
