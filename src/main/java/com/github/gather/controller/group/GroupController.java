package com.github.gather.controller.group;

import com.github.gather.dto.request.group.CreateGroupRequest;
import com.github.gather.dto.request.group.UpdateGroupInfoRequest;
import com.github.gather.security.JwtTokenProvider;
import com.github.gather.service.group.GroupServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {
    private final GroupServiceImpl groupService;
    private final JwtTokenProvider jwtTokenProvider;

    //모임생성
    @PostMapping(value = "/create")
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest newGroupRequest, HttpServletRequest request) {
        //토큰을 받아온다.
        String userToken = jwtTokenProvider.resolveToken(request);
        //해당토큰을 가지고 있는 유저(이메일?)를 가지고 온다.
        String userEmail = jwtTokenProvider.getUserEmail(userToken); //유저이메일인지 확인
        //이 유저의 모임을 만든다.
        //반환값은, CreatedGroupResponse (newGroup,user,role)
        return ResponseEntity.status(200).body(groupService.createGroup(userEmail,newGroupRequest));
    }

    //모임수정 -- 방장권한 가진 사람만 가능.
    @PutMapping(value = "/update/{groupId}")
    public ResponseEntity<?> modifyGroupInfo(@PathVariable Long groupId, @RequestBody UpdateGroupInfoRequest updateGroupInfoRequest, HttpServletRequest request) {
        //토큰을 받아온다.
        String userToken = jwtTokenProvider.resolveToken(request);
        //해당토큰을 가지고 있는 유저(email)를 가지고 온다.
        String userEmail = jwtTokenProvider.getUserEmail(userToken); //유저이메일인지 확인

        //group info 수정
        //반환값은, ModifiedGroupInfoResponse
        return ResponseEntity.status(200).body(groupService.modifyGroupInfo(userEmail,groupId,updateGroupInfoRequest));
    }


}
