package com.github.gather.dto.response;

import com.github.gather.entity.GroupTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupTableResponse {

    private Long groupId;
    private String title;
    private String image;
    private String description;


    // GroupTable을 GroupTableResponse로 변환하는 정적 메서드
    public static GroupTableResponse from(GroupTable groupTable) {
        GroupTableResponse response = new GroupTableResponse();
        response.setGroupId(groupTable.getGroupId());
        response.setTitle(groupTable.getTitle());
        response.setImage(groupTable.getImage());
        response.setDescription(groupTable.getDescription());
        // ... 다른 필드에 대한 매핑 추가

        return response;
    }
}
