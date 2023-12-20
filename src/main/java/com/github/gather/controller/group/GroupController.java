package com.github.gather.controller.group;

import com.github.gather.dto.request.group.CreateGroupRequest;
import com.github.gather.dto.request.group.UpdateGroupInfoRequest;
import com.github.gather.dto.response.group.*;
import com.github.gather.exception.group.GroupNotFoundException;
import com.github.gather.security.JwtTokenProvider;
import com.github.gather.service.group.GroupServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {
    private final GroupServiceImpl groupService;
    private final JwtTokenProvider jwtTokenProvider;

    //모임생성
    @PostMapping(value = "/create")
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest newGroupRequest, HttpServletRequest request) {
        String userToken = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(userToken); //유저이메일인지 확인
        return ResponseEntity.status(200).body(groupService.createGroup(userEmail, newGroupRequest));
    }

    //모임수정 -- 방장권한
    @PutMapping(value = "/update/{groupId}")
    public ResponseEntity<?> modifyGroupInfo(@PathVariable Long groupId, @RequestBody UpdateGroupInfoRequest updateGroupInfoRequest, HttpServletRequest request) {
        String userToken = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(userToken); //유저이메일인지 확인
        return ResponseEntity.status(200).body(groupService.modifyGroupInfo(userEmail, groupId, updateGroupInfoRequest));
    }

    //모임 삭제 --방장권한
    @DeleteMapping(value = "/delete/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId, HttpServletRequest request) {
        String userToken = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(userToken);
        String result = groupService.deleteGroup(userEmail, groupId);
        return ResponseEntity.status(200).body(result);
    }

    //모임 전체 조회
    @GetMapping(value = "/all")
    public ResponseEntity<?> searchAllGroups(HttpServletRequest request) {
        List<GroupListResponse> allGroups = groupService.searchAllGroups();
        return ResponseEntity.status(200).body(allGroups);
    }

    //모임 상세 조회 --없는 groupId면 조회 예외처리
    @GetMapping(value = "/detail/{groupId}")
    public ResponseEntity<?> getGroupDetail(@PathVariable Long groupId, HttpServletRequest request) {
        GroupDetailResponse groupDetail = groupService.getGroupDetail(groupId);
        return ResponseEntity.status(200).body(groupDetail);
    }

    //모임 멤버 조회 -- 모임에 가입한 유저만?
    @GetMapping(value = "/groupMembers/{groupId}")
    public ResponseEntity<?> findGroupMemebers(@PathVariable Long groupId, HttpServletRequest request) {
        String userToken = jwtTokenProvider.resolveToken(request);
        if (userToken != null) {
            List<GroupMemberListResponse> groupMembers = groupService.findGroupMembers(groupId);
            return ResponseEntity.status(200).body(groupMembers);
        } else {
            throw new GroupNotFoundException();
        }
    }

    //카테고리별 모임 조회 --예외처리 필요
    @GetMapping(value = "/category/{categoryId}")
    public ResponseEntity<?> searchGroupsByCategoryId(@PathVariable Long categoryId, HttpServletRequest request) {
        List<GroupListByCategoryResponse> groupListByCategoryId = groupService.searchGroupsByCategoryId(categoryId);
        return ResponseEntity.status(200).body(groupListByCategoryId);

    }

    //지역별 모임 조회 --예외처리 필요
    @GetMapping(value = "/location/{locationId}")
    public ResponseEntity<?> searchGroupsByLocationId(@PathVariable Long locationId, HttpServletRequest request) {
        List<GroupListByLocationResponse> groupListByLocationId = groupService.searchGroupsByLocationId(locationId);
        return ResponseEntity.status(200).body(groupListByLocationId);
    }

    //제목으로 모임 조회 --예외처리 필요
    @GetMapping(value = "/title/{title}")
    public ResponseEntity<?> findByTitleContaining(@PathVariable String title, HttpServletRequest request) {
        List<GroupListByTitleResponse> groupListByTitle = groupService.findByTitleContaining(title);
        return ResponseEntity.status(200).body(groupListByTitle);
    }


}
