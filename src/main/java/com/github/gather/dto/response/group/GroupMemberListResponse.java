package com.github.gather.dto.response.group;

import com.github.gather.entity.Role.GroupMemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberListResponse {

    private Long userId;
    private String email;
    private String nickname;
    private String image;
    private GroupMemberRole role;

}
