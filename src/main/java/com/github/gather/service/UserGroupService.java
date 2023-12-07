package com.github.gather.service;

import com.github.gather.dto.request.MyGroupRequest;
import com.github.gather.dto.response.MyGroupResponse;
import com.github.gather.entity.UserGroup;
import com.github.gather.repositroy.GroupRepository;
import com.github.gather.repositroy.UserGroupRepository;
import com.github.gather.repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;


    @Autowired
    public UserGroupService(UserGroupRepository userGroupRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.userGroupRepository = userGroupRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

//    public List<MyGroupResponse> getMyGroups(MyGroupRequest myGroupRequest) {
//        String userEmail = myGroupRequest.getUserEmail();
//        List<UserGroup> userGroups = userGroupRepository.findByUserEmail(userEmail);
//
//        return userGroups.stream()
//                .map(userGroup -> {
//                    MyGroupResponse myGroupResponse = new MyGroupResponse();
//                    MyGroupResponse.setGroupId(userGroup.getGroup().getId());
//                    MyGroupResponse.setGroupName(userGroup.getGroup().getName());
//
//                    // 다른 필요한 정보들을 설정
//                    return myGroupResponse;
//                })
//                .collect(Collectors.toList());

    }

