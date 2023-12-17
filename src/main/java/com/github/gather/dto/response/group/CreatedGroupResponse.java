package com.github.gather.dto.response.group;

import com.github.gather.entity.GroupTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatedGroupResponse {

    private GroupTable group;
    private String email;
    private String role;
}
