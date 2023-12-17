package com.github.gather.service;

import com.github.gather.entity.GroupMember;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.Role.GroupMemberRole;
import com.github.gather.entity.User;
import com.github.gather.repositroy.group.GroupMemberRepository;
import com.github.gather.service.group.GroupServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class GroupJoinService {

    private final GroupServiceImpl groupService;
    private final GroupMemberRepository groupMemberRepository;

    public GroupJoinService(GroupServiceImpl groupService, GroupMemberRepository groupMemberRepository){
        this.groupService = groupService;
        this.groupMemberRepository = groupMemberRepository;
    }

    public void joinGroup(Long groupId, User user) {
        GroupTable group = groupService.getGroupById(groupId);

        if (groupMemberRepository.existsByGroupIdAndUserId(group, user)) {
            throw new IllegalArgumentException("이미 그룹의 멤버입니다.");
        }

        Long currentMembers = groupMemberRepository.countByGroupId(group);

        if (currentMembers >= group.getMaxMembers()) {
            throw new IllegalArgumentException("그룹의 최대 멤버 수를 초과했습니다.");
        }

        GroupMember newMember = new GroupMember(user, group, GroupMemberRole.MEMBER);
        groupMemberRepository.save(newMember);
    }
}

