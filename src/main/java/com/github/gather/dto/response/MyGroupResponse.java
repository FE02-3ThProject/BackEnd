package com.github.gather.dto.response;

public class MyGroupResponse {

    private Long groupId;
    private String name;
    private String description;

    public MyGroupResponse(Long groupId, String name, String description) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
    }


    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
