package com.github.gather.service.group;

import com.github.gather.dto.request.group.CreateGroupRequest;
import com.github.gather.dto.request.group.UpdateGroupInfoRequest;
import com.github.gather.dto.response.group.*;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;

import java.util.List;

public interface GroupService {
    CreatedGroupResponse createGroup(String userEmail, CreateGroupRequest newGroupRequest);


    UpdatedGroupInfoResponse modifyGroupInfo(String userEmail, Long groupId, UpdateGroupInfoRequest updateGroupInfoRequest);

    String deleteGroup(String userEmail, Long groupId);

    List<GroupListByCategoryResponse> searchGroupsByCategoryId(Long categoryId);
    List<GroupListByLocationResponse> searchGroupsByLocationId(Long locationId);

    List<GroupListByTitleResponse> findByTitleContaining(String title);

    List<GroupListResponse> searchAllGroups();

    GroupTable getGroupById(Long id);

    void transferLeader(Long groupId, Long newLeaderId, User currentUser);

    void kickMember(Long groupId, Long userId, User currentUser);


    GroupDetailResponse getGroupDetail(Long groupId);

    List<GroupMemberListResponse> findGroupMembers(Long groupId);
}
