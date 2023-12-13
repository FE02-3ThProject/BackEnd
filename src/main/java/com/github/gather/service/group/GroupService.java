package com.github.gather.service.group;

import com.github.gather.dto.request.group.CreateGroupRequest;
import com.github.gather.dto.request.group.UpdateGroupInfoRequest;
import com.github.gather.dto.response.group.CreatedGroupResponse;
import com.github.gather.dto.response.group.UpdatedGroupInfoResponse;

public interface GroupService {
    CreatedGroupResponse createGroup(String userEmail, CreateGroupRequest newGroupRequest);


    UpdatedGroupInfoResponse modifyGroupInfo(String userEmail, Long groupId, UpdateGroupInfoRequest updateGroupInfoRequest);
}
