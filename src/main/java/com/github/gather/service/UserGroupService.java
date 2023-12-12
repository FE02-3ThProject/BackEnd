package com.github.gather.service;


import com.github.gather.entity.User;
import com.github.gather.entity.UserGroup;
import com.github.gather.exception.UserGroupNotFoundException;
import com.github.gather.repositroy.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;

    public List<UserGroup> getJoinedUserGroups(User user){
        return userGroupRepository.findByUser(user);
    }

    public List<UserGroup> getBookmarkedUserGroups(User user) {
        return userGroupRepository.findByBookmarkedUsersContaining(user);
    }

    public void bookmarkUserGroup(User user, Long groupId) {
        UserGroup userGroup = userGroupRepository.findById(groupId)
                .orElseThrow(() -> new UserGroupNotFoundException("모임을 찾을 수 없습니다."));

        userGroup.getBookmarkedUsers().add(user);
        userGroupRepository.save(userGroup);
    }

    public void unbookmarkUserGroup(User user, Long groupId) {
        userGroupRepository.deleteByBookmarkedUsersContainingAndGroupGroupId(user, groupId);
    }

}
