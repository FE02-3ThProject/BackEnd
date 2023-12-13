package com.github.gather.service.group;

import com.github.gather.dto.request.group.CreateGroupRequest;
import com.github.gather.dto.request.group.UpdateGroupInfoRequest;
import com.github.gather.dto.response.group.CreatedGroupResponse;
import com.github.gather.dto.response.group.UpdatedGroupInfoResponse;
import com.github.gather.entity.*;
import com.github.gather.entity.Role.GroupMemberRole;
import com.github.gather.exception.group.*;
import com.github.gather.repositroy.UserRepository;
import com.github.gather.repositroy.group.CategoryRepository;
import com.github.gather.repositroy.group.GroupMemberRepository;
import com.github.gather.repositroy.group.GroupRepository;
import com.github.gather.repositroy.group.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
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

    User user;
    GroupTable group;
    Location location;
    Category category;


    // 1. 모임생성
    @Override
    public CreatedGroupResponse createGroup(String userEmail, CreateGroupRequest newGroupRequest) {
        //categoryId에 해당하는 Category Entity 찾아오기 (getById는 JPA에서 더 이상 권장하지 않음.)
        Category foundCategory = getCategory(newGroupRequest.getCategoryId());

        //locationId에 해당하는 Location Entity 찾아오기
        Location foundLocation = getLocation(newGroupRequest.getLocationId());

        //userEmail -> User(Entity) 찾아오기 //pwd는 필요없으니까 진호님께 물어보기.
        User foundUser = getUserByEmail(userEmail);

        //Category categoryId, Location locationId, String title, String image, String description, Integer maxMembers, LocalDate createdAt, Boolean isDeleted
        GroupTable newGroup = new GroupTable(foundCategory, foundLocation, newGroupRequest.getTitle(), newGroupRequest.getImage(), newGroupRequest.getDescription(), newGroupRequest.getMaxMembers(), newGroupRequest.getCreatedAt(), false);

        //요청된 new 모임(Entity)을 DB에 저장.
        groupRepository.save(newGroup);

        //새로운 모임 멤버 객체를 생성 (생성된 모임을 할당, 유저를 방장으로).
        //User userId, GroupTable groupId, String role
        GroupMember newGroupMember = new GroupMember(foundUser, newGroup, GroupMemberRole.LEADER);

        //새로운 모임 멤버를 DB에 저장.
        groupMemberRepository.save(newGroupMember);

        return CreatedGroupResponse.builder()
                .group(newGroup)
                .email(foundUser.getEmail())
                .role(String.valueOf(newGroupMember.getRole()))
                .build();
    }

    // 2. 모임수정
    @Override
    public UpdatedGroupInfoResponse modifyGroupInfo(String userEmail, Long groupId, UpdateGroupInfoRequest updateGroupInfo) {

        //모임 조회
        GroupTable foundGroup = getGroup(groupId);

        //수정할 category 조회
        Category updatedCategory = getCategory(updateGroupInfo.getCategoryId());

        //수정할 location 조회
        Location updatedLocation = getLocation(updateGroupInfo.getLocationId());

        //찾아온 모임에 대한 Leader 모임멤버 조회
        GroupMember foundGroupMember = groupMemberRepository.findGroupMemberByRoleLeader(foundGroup.getGroupId());


        //찾아온 Leader 모임멤버의 email과 로그인한 유저의 email이 일치하면 모임 수정 가능
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
        Optional<GroupTable> foundGroupOpt = groupRepository.findById(groupId);
        if (foundGroupOpt.isPresent()) {
            group = foundGroupOpt.get();
            return group;
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


}


