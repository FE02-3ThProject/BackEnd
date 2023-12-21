package com.github.gather.service;

import com.github.gather.entity.*;
import com.github.gather.exception.UserNotFoundException;
import com.github.gather.repositroy.BookmarkRepository;
import com.github.gather.repositroy.UserGroupRepository;
import com.github.gather.repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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


    // 사용자가 가입한 그룹 목록을 가져오는 메서드
    public List<Long> getJoinedUserGroupIds(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));

        // UserGroupTable에서 groupId 값을 추출하여 반환
        List<Long> joinedGroupIds = user.getJoinGroups().stream()
                .map(UserGroupTable::getGroupId)
                .collect(Collectors.toList());

        return Collections.unmodifiableList(new ArrayList<>(joinedGroupIds));
    }



    // 사용자의 즐겨찾기한 그룹의 groupId 목록을 가져오는 메서드
// 사용자의 즐겨찾기한 그룹의 groupId 목록을 가져오는 메서드
    public List<Long> getBookmarkedUserGroupIds(String email) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserEmail(email);

        // Bookmark에서 groupId 필드에 직접 접근하여 값을 추출하여 반환
        List<Long> bookmarkedGroupIds = bookmarks.stream()
                .map(bookmark -> bookmark.getBookmarkIdx())
                .collect(Collectors.toList());

        return bookmarkedGroupIds;
    }


    // 모임 즐겨찾기 삭제
    public void unbookmarkUserGroup (User user, Long groupId){
        userGroupRepository.findById(groupId)
                .map(userGroup -> {
                    userGroup.getBookmarkedUsers().remove(user);
                    userGroupRepository.save(userGroup);
                    return userGroup;
                })
                .orElseThrow(() -> new UserNotFoundException("모임을 찾을 수 없습니다: " + groupId));
    }

}