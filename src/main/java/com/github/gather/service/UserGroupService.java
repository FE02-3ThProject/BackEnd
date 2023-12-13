package com.github.gather.service;


import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;
import com.github.gather.entity.UserGroup;
import com.github.gather.exception.UserGroupNotFoundException;
import com.github.gather.repositroy.GroupTableRepository;
import com.github.gather.repositroy.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final GroupTableRepository groupTableRepository;

    public List<UserGroup> getJoinedUserGroups(User user){
        return userGroupRepository.findByUser(user);
    }

    public List<UserGroup> getBookmarkedUserGroups(User user) {
        return userGroupRepository.findByBookmarkedUsersContaining(user);
    }

    public void bookmarkUserGroup(User user, Long groupId) {
        UserGroup userGroup = userGroupRepository.findById(groupId)
                .orElseThrow(() -> new UserGroupNotFoundException("모임을 찾을 수 없습니다."));

        GroupTable groupTable = groupTableRepository.findById(groupId)
                .orElseThrow(() -> new UserGroupNotFoundException("모임을 찾을 수 없습니다."));


        userGroup.getBookmarkedUsers().add(user);
        userGroup.setGroup(groupTable);
        userGroupRepository.save(userGroup);

    }

    public void unbookmarkUserGroup(User user, Long groupId) {
        userGroupRepository.deleteByBookmarkedUsersContainingAndGroupGroupId(user, groupId);
    }

}
