package com.github.gather.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGroupRequest {

    private Long userId;
    private Long groupId;

    public UserGroupRequest(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}
