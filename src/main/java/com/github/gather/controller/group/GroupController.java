package com.github.gather.controller.group;

import com.github.gather.dto.request.group.CreateGroupRequest;
import com.github.gather.dto.request.group.UpdateGroupInfoRequest;
import com.github.gather.dto.response.group.*;
import com.github.gather.exception.group.GroupNotFoundException;
import com.github.gather.security.JwtTokenProvider;
import com.github.gather.service.group.GroupServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Group",description = "모임 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {
    private final GroupServiceImpl groupService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "모임 생성" , description = "모임 생성을 진행합니다.")
    @PostMapping(value = "/create")
    public ResponseEntity<?> createGroup(@RequestParam("file") MultipartFile image,
                                         @RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("maxMembers") Integer maxMembers,
                                         @RequestParam("categoryId") Long categoryId,
                                         @RequestParam("locationId") Long locationId, HttpServletRequest request)  {
        String userToken = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(userToken);

        CreateGroupRequest newGroupRequest = new CreateGroupRequest(categoryId,locationId,title,description,maxMembers);

        return ResponseEntity.status(200).body(groupService.createGroup(userEmail, newGroupRequest, image));
    }

    @Operation(summary = "모임 수정" , description = "모임 수정을 진행합니다.")
    @PutMapping(value = "/update/{groupId}")
    public ResponseEntity<?> modifyGroupInfo(@PathVariable Long groupId,
                                             @RequestParam(value = "file", required = false) MultipartFile newImage,
                                             @RequestParam("title") String title,
                                             @RequestParam("description") String description,
                                             @RequestParam("maxMembers") Integer maxMembers,
                                             @RequestParam("categoryId") Long categoryId,
                                             @RequestParam("locationId") Long locationId,
                                             HttpServletRequest request) {
        String userToken = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(userToken);

        UpdateGroupInfoRequest updateGroupInfoRequest = new UpdateGroupInfoRequest(categoryId, locationId, title, description, maxMembers);

        return ResponseEntity.status(200).body(groupService.modifyGroupInfo(userEmail, groupId, updateGroupInfoRequest, newImage));
    }

    @Operation(summary = "모임 삭제" , description = "모임 삭제를 진행합니다.")
    @DeleteMapping(value = "/delete/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId, HttpServletRequest request) {
        String userToken = jwtTokenProvider.resolveToken(request);
        String userEmail = jwtTokenProvider.getUserEmail(userToken);
        String result = groupService.deleteGroup(userEmail, groupId);
        return ResponseEntity.status(200).body(result);
    }

    @Operation(summary = "모임 전체 조회" , description = "모임 전체 조희를 진행합니다.")
    @GetMapping(value = "/all")
    public ResponseEntity<?> searchAllGroups() {
        List<GroupListResponse> allGroups = groupService.searchAllGroups();
        return ResponseEntity.status(200).body(allGroups);
    }

    @Operation(summary = "모임 상세 조회" , description = "모임 상세 조회를 진행합니다.")
    @GetMapping(value = "/detail/{groupId}")
    public ResponseEntity<?> getGroupDetail(@PathVariable Long groupId) {
        GroupDetailResponse groupDetail = groupService.getGroupDetail(groupId);
        return ResponseEntity.status(200).body(groupDetail);
    }

    @Operation(summary = "모임 멤버 조회" , description = "모임 멤버 조회를 진행합니다.")
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
    @Operation(summary = "카테고리별 모임 조회" , description = "카테고리별 모임 조회를 진행합니다.")
    @GetMapping(value = "/category/{categoryId}")
    public ResponseEntity<?> searchGroupsByCategoryId(@PathVariable Long categoryId) {
        List<GroupListByCategoryResponse> groupListByCategoryId = groupService.searchGroupsByCategoryId(categoryId);
        return ResponseEntity.status(200).body(groupListByCategoryId);

    }

    //지역별 모임 조회 --예외처리 필요
    @Operation(summary = "지역별 모임 조회" , description = "지역별 모임 조회를 진행합니다.")
    @GetMapping(value = "/location/{locationId}")
    public ResponseEntity<?> searchGroupsByLocationId(@PathVariable Long locationId) {
        List<GroupListByLocationResponse> groupListByLocationId = groupService.searchGroupsByLocationId(locationId);
        return ResponseEntity.status(200).body(groupListByLocationId);
    }

    //제목으로 모임 조회 --예외처리 필요
    @Operation(summary = "제목으로 모임 조회" , description = "제목으로 모임 조회를 진행합니다.")
    @GetMapping(value = "/title/{title}")
    public ResponseEntity<?> findByTitleContaining(@PathVariable String title) {
        List<GroupListByTitleResponse> groupListByTitle = groupService.findByTitleContaining(title);
        return ResponseEntity.status(200).body(groupListByTitle);
    }


}
