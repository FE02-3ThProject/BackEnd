package com.github.gather.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGroupResponse {

    private boolean success;
    private String message;

    public UserGroupResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
