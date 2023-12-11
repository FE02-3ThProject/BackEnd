package com.github.gather.service;


import com.github.gather.dto.response.MyGroupResponse;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;
import com.github.gather.entity.UserGroup;
import com.github.gather.exception.UserRuntimeException;
import com.github.gather.repositroy.UserGroupRepository;
import com.github.gather.repositroy.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    public UserGroupService(UserGroupRepository userGroupRepository, UserRepository userRepository) {
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
    }

    public List<MyGroupResponse> getMyGroups(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserRuntimeException("회원 정보를 찾을 수 없습니다."));

        List<UserGroup> userGroups = userGroupRepository.findByUser(user);

        List<MyGroupResponse> myGroupResponses = new ArrayList<>();
        for (UserGroup userGroup : userGroups) {
            GroupTable groupTable = userGroup.getGroup();
            MyGroupResponse myGroupResponse = new MyGroupResponse(groupTable.getGroupId(), groupTable.getCategoryId().getName(), groupTable.getDescription());
            myGroupResponses.add(myGroupResponse);
        }
        return myGroupResponses;
    }
}
