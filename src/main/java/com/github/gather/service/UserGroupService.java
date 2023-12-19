package com.github.gather.service;

import com.github.gather.dto.response.BookMarkResponse;
import com.github.gather.entity.*;
import com.github.gather.exception.UserNotFoundException;
import com.github.gather.repositroy.BookmarkRepository;
import com.github.gather.repositroy.GroupTableRepository;
import com.github.gather.repositroy.UserGroupRepository;
import com.github.gather.repositroy.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserGroupService {


    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserGroupRepository userGroupRepository;

    @Autowired
    public UserGroupService(UserRepository userRepository, BookmarkRepository bookmarkRepository, UserGroupRepository userGroupRepository) {
        this.userRepository = userRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.userGroupRepository = userGroupRepository;
    }


    public List<Long> getGroupIdsFromJoinedUserGroups(String email) {
        List<GroupTable> joinedUserGroups = getJoinedUserGroups(email);

        // Extract group_id values from GroupTable objects
        List<Long> groupIds = joinedUserGroups.stream()
                .map(GroupTable::getGroupId)
                .collect(Collectors.toList());

        return groupIds;
    }

    // 사용자가 가입한 그룹 목록을 가져오는 메서드
    public List<GroupTable> getJoinedUserGroups(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));

        // Return the list of groups the user has joined
        return Optional.ofNullable(user.getUserGroups())
                .orElse(Collections.emptyList())
                .stream()
                .map(UserGroup::getGroup)
                .collect(Collectors.toList());
    }

    // 사용자가 즐겨찾은 그룹 목록을 가져오는 메서드
    public List<Bookmark> getBookmarkedUserGroups(String email) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserEmail(email);
        if (bookmarks.isEmpty()) {
            // If the user is not found, you can return an empty list or do other processing.
            return Collections.emptyList();
        }
        return bookmarks;
    }




    // 모임 즐겨찾기 삭제
    public void unbookmarkUserGroup (User user, Long groupId){
        userGroupRepository.findById(groupId)
                .map(userGroup -> {
                    userGroup.getBookmarkedUsers().remove(user);
                    userGroupRepository.save(userGroup);
                    return userGroup;
                })
                .orElseThrow(() -> new UserNotFoundException("그룹을 찾을 수 없습니다: " + groupId));
    }


}


