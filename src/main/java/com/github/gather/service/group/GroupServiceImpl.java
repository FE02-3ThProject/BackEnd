package com.github.gather.service.group;

import com.github.gather.dto.request.group.CreateGroupRequest;
import com.github.gather.dto.request.group.UpdateGroupInfoRequest;
import com.github.gather.dto.response.group.*;
import com.github.gather.entity.*;
import com.github.gather.entity.Role.GroupMemberRole;
import com.github.gather.exception.ErrorException;
import com.github.gather.exception.group.*;
import com.github.gather.repositroy.CategoryRepository;
import com.github.gather.repositroy.ChatRoomRepository;
import com.github.gather.repositroy.LocationRepository;
import com.github.gather.repositroy.UserRepository;
import com.github.gather.repositroy.group.GroupMemberRepository;
import com.github.gather.repositroy.group.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    User user;
    Location location;
    Category category;

    // 1. 모임생성
    @Override
    public CreatedGroupResponse createGroup(String userEmail, CreateGroupRequest newGroupRequest) {
        Category foundCategory = getCategory(newGroupRequest.getCategoryId());
        Location foundLocation = getLocation(newGroupRequest.getLocationId());
        User foundUser = getUserByEmail(userEmail);
        GroupTable newGroup = new GroupTable(foundCategory, foundLocation, newGroupRequest.getTitle(), newGroupRequest.getImage(), newGroupRequest.getDescription(), newGroupRequest.getMaxMembers(), LocalDate.now(), false);
        groupRepository.save(newGroup);
        GroupMember newGroupMember = new GroupMember(foundUser, newGroup, GroupMemberRole.LEADER);
        groupMemberRepository.save(newGroupMember);

        // 채팅방 생성
        ChatRoom newChatRoom = new ChatRoom(newGroup);
        chatRoomRepository.save(newChatRoom);


        return CreatedGroupResponse.builder()
                .group(newGroup)
                .email(foundUser.getEmail())
                .role(String.valueOf(newGroupMember.getRole()))
                .build();
    }

    @Override
    public GroupTable getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 그룹이 없습니다."));
    }

    // 2. 모임수정 -- 방장 고유권한
    @Override
    public UpdatedGroupInfoResponse modifyGroupInfo(String userEmail, Long groupId, UpdateGroupInfoRequest updateGroupInfo) {

        GroupTable foundGroup = getGroup(groupId);
        Category updatedCategory = getCategory(updateGroupInfo.getCategoryId());
        Location updatedLocation = getLocation(updateGroupInfo.getLocationId());
        GroupMember foundGroupMember = groupMemberRepository.findGroupMemberByRoleLeader(foundGroup.getGroupId());
        if (foundGroupMember.getUserId().getEmail().equals(userEmail)) {
            foundGroup.updateGroupInfo(
                    updatedCategory,
                    updatedLocation,
                    updateGroupInfo.getTitle(),
                    updateGroupInfo.getDescription(),
                    updateGroupInfo.getImage(),
                    updateGroupInfo.getMaxMembers(),
                    foundGroup.getCreatedAt()
            );

            //수정한 모임 저장
            GroupTable updatedGroup = groupRepository.save(foundGroup);

            return UpdatedGroupInfoResponse.builder()
                    .groupId(updatedGroup.getGroupId())
                    .categoryId(updatedGroup.getCategoryId().getCategoryId())
                    .locationId(updatedGroup.getLocationId().getLocationId())
                    .title(updatedGroup.getTitle())
                    .image(updatedGroup.getImage())
                    .description(updatedGroup.getDescription())
                    .maxMembers(updatedGroup.getMaxMembers())
                    .createdAt(updatedGroup.getCreatedAt())
                    .build();
        } else {
            throw new MemberNotAllowedException();
        }
    }

    // 3. 모임 삭제 --방장 고유 권한
    @Override
    public String deleteGroup(String userEmail, Long groupId) {
        GroupTable foundGroup = getGroup(groupId);
        GroupMember foundGroupMember = groupMemberRepository.findGroupMemberByRoleLeader(foundGroup.getGroupId());

        if (foundGroupMember.getUserId().getEmail().equals(userEmail)) {
            groupMemberRepository.deleteGroupMembersByGroupId(foundGroup.getGroupId());
            groupRepository.deleteById(foundGroup.getGroupId());

            return "해당 모임과 모임멤버 모두 삭제되었습니다.";
        } else {
            throw new MemberNotAllowedException();
        }
    }

    // 4. 모임 전체 조회
    @Override
    public List<GroupListResponse> searchAllGroups() {
        List<GroupListResponse> groupList = new ArrayList<>();

        List<GroupTable> allGroups = groupRepository.searchAllGroups();
        for (GroupTable groupTable : allGroups) {
            GroupMember groupLeader = groupMemberRepository.findGroupMemberByRoleLeader(groupTable.getGroupId());
            GroupListResponse group = GroupListResponse.builder()
                    .groupId(groupTable.getGroupId())
                    .locationName(groupTable.getLocationId().getName())
                    .categoryName(groupTable.getCategoryId().getName())
                    .title(groupTable.getTitle())
                    .description(groupTable.getDescription())
                    .image(groupTable.getImage())
                    .maxMembers(groupTable.getMaxMembers())
                    .createdAt(groupTable.getCreatedAt())
                    .leaderEmail(groupLeader.getUserId().getEmail())
                    .build();
            groupList.add(group);
        }
        return  groupList;
    }


    //모임 상세 정보 조회 -- 예외처리 필요
    @Override
    public GroupDetailResponse getGroupDetail(Long groupId) {
        //groupId로 GroupTable 찾아오기
        GroupTable existingGroup = groupRepository.findbyGroupId(groupId);

        //그룹 리더의 이메일 찾아오기
        GroupMember groupLeader  = groupMemberRepository.findGroupMemberByRoleLeader(groupId);
        String leaderEmail = groupLeader.getUserId().getEmail();

        //그룹에 가입된 총인원수 가져오기
        Long countedGroupMembers = groupMemberRepository.countGroupJoinedMembers(groupId);

        //리더 프로필사진 가져오기
        String leaderProfilePicture = groupLeader.getUserId().getImage();

        //리더 닉네임 가져오기
        String leaderNickname = groupLeader.getUserId().getNickname();

        //GroupDetailResponse로 변환 후 반환
        return GroupDetailResponse.builder()
                .groupId(existingGroup.getGroupId())
                .locationName(existingGroup.getLocationId().getName())
                .categoryName(existingGroup.getCategoryId().getName())
                .title(existingGroup.getTitle())
                .description(existingGroup.getDescription())
                .image(existingGroup.getImage())
                .maxMembers(existingGroup.getMaxMembers())
                .createdAt(existingGroup.getCreatedAt())
                .leaderEmail(leaderEmail)
                .leaderNickname(leaderNickname)
                .leaderProfilePicture(leaderProfilePicture)
                .joinedGroupMembers(countedGroupMembers)
                .build();


    }

    // 모임 멤버 조회 --예외처리 필요
    @Override
    public List<GroupMemberListResponse> findGroupMembers(Long groupId) {
        List<GroupMemberListResponse> groupMemberList = new ArrayList<>();

        List<GroupMember> groupMembers = groupMemberRepository.findGroupMemebersByGroupId(groupId);
        for(GroupMember foundGroupMember : groupMembers){
            GroupMemberListResponse groupMember = GroupMemberListResponse.builder()
                    .email(foundGroupMember.getUserId().getEmail())
                    .nickname(foundGroupMember.getUserId().getNickname())
                    .image(foundGroupMember.getUserId().getImage())
                    .role(foundGroupMember.getRole())
                    .build();

            groupMemberList.add(groupMember);

        }

        return groupMemberList;
    }

    @Override
    public List<GroupListByCategoryResponse> searchGroupsByCategoryId(Long categoryId) {
        List<GroupListByCategoryResponse> groupList = new ArrayList<>();

        List<GroupTable> groupListByCategoryId = groupRepository.findByCategoryId(categoryId);
        if (groupListByCategoryId != null) {
            for (GroupTable groupTable : groupListByCategoryId) {
                GroupListByCategoryResponse group = GroupListByCategoryResponse.builder()
                        .groupId(groupTable.getGroupId())
                        .locationName(groupTable.getLocationId().getName())
                        .categoryName(groupTable.getCategoryId().getName())
                        .title(groupTable.getTitle())
                        .description(groupTable.getDescription())
                        .image(groupTable.getImage())
                        .maxMembers(groupTable.getMaxMembers())
                        .createdAt(groupTable.getCreatedAt())
                        .build();
                groupList.add(group);
            }
            return  groupList;
        } else {
            throw new GroupNotFoundException();
        }
    }

    @Override
    public List<GroupListByLocationResponse> searchGroupsByLocationId(Long locationId) {
        List<GroupListByLocationResponse> groupList = new ArrayList<>();


        List<GroupTable> groupListByLocationId = groupRepository.findByLocationId(locationId);
        if (groupListByLocationId != null) {
            for (GroupTable groupTable : groupListByLocationId) {
                GroupListByLocationResponse group = GroupListByLocationResponse.builder()
                        .groupId(groupTable.getGroupId())
                        .locationName(groupTable.getLocationId().getName())
                        .categoryName(groupTable.getCategoryId().getName())
                        .title(groupTable.getTitle())
                        .description(groupTable.getDescription())
                        .image(groupTable.getImage())
                        .maxMembers(groupTable.getMaxMembers())
                        .createdAt(groupTable.getCreatedAt())
                        .build();
                groupList.add(group);
            }
            return  groupList;
        } else{
            throw new GroupNotFoundException();
        }
    }

    @Override
    public List<GroupListByTitleResponse> findByTitleContaining(String title) {
        List<GroupListByTitleResponse> groupList = new ArrayList<>();
        List<GroupTable> groupListByTitle = groupRepository.findByTitleContaining(title);
        if (groupListByTitle != null) {
            for (GroupTable groupTable : groupListByTitle) {
                GroupListByTitleResponse group = GroupListByTitleResponse.builder()
                        .groupId(groupTable.getGroupId())
                        .locationName(groupTable.getLocationId().getName())
                        .categoryName(groupTable.getCategoryId().getName())
                        .title(groupTable.getTitle())
                        .description(groupTable.getDescription())
                        .image(groupTable.getImage())
                        .maxMembers(groupTable.getMaxMembers())
                        .createdAt(groupTable.getCreatedAt())
                        .build();
                groupList.add(group);
            }
            return  groupList;
        } else {
            throw new GroupNotFoundException();
        }

    }

    //유저 email로 해당 유저 찾기
    public User getUserByEmail(String userEmail) {
        Optional<User> foundUserOpt = userRepository.findByEmail(userEmail);
        if (foundUserOpt.isPresent()) {
            user = foundUserOpt.get();
            return user;
        } else {
            throw new UserNotFoundException();
        }
    }

    //groupId로 해당 group 찾기 (수정, 삭제, 조회시 사용)
    public GroupTable getGroup(Long groupId) {
        GroupTable foundGroup = groupRepository.findbyGroupId(groupId);
        if (foundGroup != null) {
            return foundGroup;
        } else {
            throw new GroupNotFoundException();
        }
    }

    //CategoryId로 Category 조회 (-> GroupTable 저장)
    public Category getCategory(Long categoryId) {
        Optional<Category> foundCategoryOpt = categoryRepository.findById(categoryId);
        if (foundCategoryOpt.isPresent()) {
            category = foundCategoryOpt.get();
            return category;
        } else {
            throw new CategoryNotFoundException();
        }
    }

    //LocationId로 Location 조회(-> GroupTable 저장)
    public Location getLocation(Long locationId) {
        Optional<Location> foundLocationOpt = locationRepository.findById(locationId);
        if (foundLocationOpt.isPresent()) {
            location = foundLocationOpt.get();
            return location;
        } else {
            throw new LocationNotFoundException();
        }
    }

    // 방장 권한 이전
    @Transactional
    public void transferLeader(Long groupId, Long newLeaderId, User currentUser) {
        GroupTable group = getGroupById(groupId);

        // 방장 여부 확인
        GroupMember currentLeader = groupMemberRepository.findByUserIdAndGroupId(currentUser, group)
                .orElseThrow(() -> new ErrorException("현재 사용자는 이 그룹의 방장이 아닙니다."));

        if (!currentLeader.getRole().equals(GroupMemberRole.LEADER)) {
            throw new ErrorException("권한이 없습니다.");
        }

        // 새로운 방장이 그룹의 멤버인지 확인
        User newLeader = userRepository.findById(newLeaderId)
                .orElseThrow(() -> new ErrorException("해당 ID의 사용자가 존재하지 않습니다."));
        GroupMember newAdminMember = groupMemberRepository.findByUserIdAndGroupId(newLeader, group)
                .orElseThrow(() -> new ErrorException("새로운 방장이 그룹의 멤버가 아닙니다."));

        // 방장 권한 변경
        currentLeader.setRole(GroupMemberRole.MEMBER);
        groupMemberRepository.save(currentLeader);

        newAdminMember.setRole(GroupMemberRole.LEADER);
        groupMemberRepository.save(newAdminMember);
    }

    // 멤버 추방
    @Override
    @Transactional
    public void kickMember(Long groupId, Long userId, User currentUser) {
        GroupTable group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ErrorException("해당 ID의 그룹이 존재하지 않습니다."));

        // 방장 여부 확인
        GroupMember currentLeader = groupMemberRepository.findByUserIdAndGroupId(currentUser, group)
                .orElseThrow(() -> new ErrorException("현재 사용자는 이 그룹의 방장이 아닙니다."));

        if (!currentLeader.getRole().equals(GroupMemberRole.LEADER)) {
            throw new ErrorException("권한이 없습니다.");
        }

        // 추방하려는 멤버 여부 확인
        User memberToKick = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException("해당 ID의 사용자가 존재하지 않습니다."));
        GroupMember member = groupMemberRepository.findByUserIdAndGroupId(memberToKick, group)
                .orElseThrow(() -> new ErrorException("추방하려는 사용자가 그룹의 멤버가 아닙니다."));

        // 그룹에서 제거
        groupMemberRepository.delete(member);
    }


}


