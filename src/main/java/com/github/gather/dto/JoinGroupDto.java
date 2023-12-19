package com.github.gather.dto;

import com.github.gather.entity.GroupTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinGroupDto {

    private Long groupId;

    public JoinGroupDto(GroupTable groupTable){
        this.groupId = groupTable.getGroupId();
    }
}
