package com.github.gather.service;

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
@RequiredArgsConstructor
public class UserGroupService {


    private final UserGroupRepository userGroupRepository;
    private final GroupTableRepository groupTableRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private String email;




    public List<GroupTable> getJoinedUserGroups(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다: " + email));
        return user.getUserGroups().stream().map(UserGroup::getGroup).collect(Collectors.toList());
    }


    public List<Bookmark> getBookmarkedUserGroups(String email) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserEmail(email);
        if (bookmarks.isEmpty()) {
            throw new UserNotFoundException("유저를 찾을 수 없습니다: " + email);
        }
        return bookmarks;
    }


    private Optional<UserGroup> getUserGroupByUser(User userToBookmark) {
        List<UserGroup> userGroups = userRepository.findAllUserGroupsByUser(userToBookmark);
        return userGroups.stream().findFirst();
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

        // 그룹 ID로 사용자 그룹을 찾는 메서드
        public Optional<UserGroup> getUserGroupById (Long groupId){
            return userGroupRepository.findById(groupId);
        }
}


