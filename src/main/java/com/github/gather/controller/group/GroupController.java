package com.github.gather.controller.group;

import com.github.gather.dto.request.group.CreateGroupRequest;
import com.github.gather.dto.request.group.UpdateGroupInfoRequest;
import com.github.gather.dto.response.group.GroupListByCategoryResponse;
import com.github.gather.dto.response.group.GroupListByLocationResponse;
import com.github.gather.dto.response.group.GroupListByTitleResponse;
import com.github.gather.exception.UserRuntimeException;
import com.github.gather.repositroy.group.GroupRepository;
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
        //토큰을 받아온다.
        String userToken = jwtTokenProvider.resolveToken(request);
        //해당토큰을 가지고 있는 유저(이메일?)를 가지고 온다.
        String userEmail = jwtTokenProvider.getUserEmail(userToken); //유저이메일인지 확인
        //이 유저의 모임을 만든다.
        //반환값은, CreatedGroupResponse (newGroup,user,role)
        return ResponseEntity.status(200).body(groupService.createGroup(userEmail,newGroupRequest));
    }

    //모임수정 -- 방장권한
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

    //모임 삭제 --방장권한
    @DeleteMapping(value = "/delete/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId,  HttpServletRequest request) {

        String userToken = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(userToken);

        String result = groupService.deleteGroup(userEmail,groupId);

        return ResponseEntity.status(200).body(result);
    }

    //카테고리별 모임 조회 --로그인필수
    @GetMapping(value = "/category/{categoryId}")
    public ResponseEntity<?> searchGroupsByCategoryId(@PathVariable Long categoryId,  HttpServletRequest request) {

        String userToken = jwtTokenProvider.resolveToken(request);

        if(userToken != null) {
            List<GroupListByCategoryResponse> groupListByCategoryId = groupService.searchGroupsByCategoryId(categoryId);
            return ResponseEntity.status(200).body(groupListByCategoryId);
        }else {
            throw new UserRuntimeException("모임 카페고리를 조회하려면 로그인이 필요합니다.");
        }
    }

    //지역별 모임 조회 --로그인 필수
    @GetMapping(value = "/location/{locationId}")
    public ResponseEntity<?> searchGroupsByLocationId(@PathVariable Long locationId,  HttpServletRequest request) {

        String userToken = jwtTokenProvider.resolveToken(request);

        if(userToken != null) {
            List<GroupListByLocationResponse> groupListByLocationId = groupService.searchGroupsByLocationId(locationId);
            return ResponseEntity.status(200).body(groupListByLocationId);
        }else {
            throw new UserRuntimeException("모임 지역을 조회하려면 로그인이 필요합니다.");
        }
    }

    //제목으로 모임 조회 -- 로그인 필수
    @GetMapping(value = "/title/{title}")
    public ResponseEntity<?> findByTitleContaining(@PathVariable String title,  HttpServletRequest request) {

        String userToken = jwtTokenProvider.resolveToken(request);

        if(userToken != null) {
            List<GroupListByTitleResponse> groupListByTitle = groupService.findByTitleContaining(title);
            return ResponseEntity.status(200).body(groupListByTitle);
        }else {
            throw new UserRuntimeException("모임 지역을 조회하려면 로그인이 필요합니다.");
        }
    }



}
