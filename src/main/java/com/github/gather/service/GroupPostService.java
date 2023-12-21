package com.github.gather.service;

import com.github.gather.dto.GroupPostDto;
import com.github.gather.entity.GroupPost;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;
import com.github.gather.exception.ExceptionMessage;
import com.github.gather.repository.GroupPostRepository;
import com.github.gather.repository.GroupTableRepository;
import com.github.gather.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupPostService {

    private final GroupPostRepository groupPostRepository;
    private final GroupTableRepository groupTableRepository;
    private final UserRepository userRepository;

    public GroupPostService(GroupPostRepository groupPostRepository, GroupTableRepository groupTableRepository, UserRepository userRepository) {
        this.groupPostRepository = groupPostRepository;
        this.groupTableRepository = groupTableRepository;
        this.userRepository = userRepository;
    }

    private GroupPost convertToEntity(GroupPostDto groupPostDto) {
        GroupTable group = groupTableRepository.findById(groupPostDto.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("모임방 정보가 없습니다."));
        User user = userRepository.findByEmailAndIsDeletedFalse(groupPostDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));
        return new GroupPost(null, group, groupPostDto.getTitle(), groupPostDto.getContent(), user, LocalDateTime.now(), groupPostDto.getEmail());
    }

    private GroupPostDto convertToDto(GroupPost groupPostEntity) {
        return new GroupPostDto(
                groupPostEntity.getPostIdx(),
                groupPostEntity.getGroupId().getGroupId(),
                groupPostEntity.getUserId().getEmail(),
                groupPostEntity.getTitle(),
                groupPostEntity.getContent(),
                groupPostEntity.getPostedAt().toLocalDate()
        );
    }

    // 게시글 등록
    public GroupPostDto createPost(GroupPostDto groupPostDto) {
        GroupPost post = convertToEntity(groupPostDto);
        GroupPost savedPost = groupPostRepository.save(post);
        return new GroupPostDto(savedPost.getPostIdx(), savedPost.getGroupId().getGroupId(), savedPost.getUserId().getEmail(), savedPost.getTitle(), savedPost.getContent(), savedPost.getPostedAt().toLocalDate());
    }

    // 게시글 조회(전채)
    public List<GroupPostDto> getPosts(Long groupId) {
        GroupTable group = groupTableRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.POST_NOT_FOUND_FOR_GET));
        List<GroupPost> groupPostEntities = groupPostRepository.findByGroupId(group);
        return groupPostEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 게시글 조회(특정)
    public GroupPostDto getPost(Long postId) {
        GroupPost post = groupPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.POST_NOT_FOUND_FOR_GET));
        return convertToDto(post);
    }

    // 게시글 수정
    public GroupPostDto updatePost(Long postId, GroupPostDto groupPostDto) {
        GroupPost post = groupPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.POST_NOT_FOUND_FOR_UPDATE));
        post.update(groupPostDto.getTitle(), groupPostDto.getContent());
        GroupPost updatedPost = groupPostRepository.save(post);
        return convertToDto(updatedPost);
    }

    // 게시글 삭제
    public void deletePost(Long postId) {
        GroupPost post = groupPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.POST_NOT_FOUND_FOR_DELETE));

        groupPostRepository.delete(post);
    }
}
