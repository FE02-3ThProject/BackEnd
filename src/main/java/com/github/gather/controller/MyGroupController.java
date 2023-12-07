package com.github.gather.controller;


import com.github.gather.dto.request.MyGroupRequest;
import com.github.gather.dto.response.MyGroupResponse;
import com.github.gather.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class MyGroupController {

    private final UserGroupService userGroupService;

    @Autowired
    public MyGroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }


    @GetMapping("/mygroup")
    public ResponseEntity<List<MyGroupResponse>> getMyGroups(@RequestParam String userEmail) {
        MyGroupRequest myGroupRequest = new MyGroupRequest();
        MyGroupRequest.setUserEmail(userEmail);

        List<MyGroupResponse> myGroups = userGroupService.getMyGroups(myGroupRequest);
        return ResponseEntity.ok(myGroups);
    }
}
