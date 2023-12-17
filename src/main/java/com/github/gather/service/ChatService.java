package com.github.gather.service;

import com.github.gather.dto.MessageDto;
import com.github.gather.entity.*;
import com.github.gather.exception.ErrorException;
import com.github.gather.repositroy.ChatParticipantRepository;
import com.github.gather.repositroy.ChatRoomRepository;
import com.github.gather.repositroy.MessageRepository;
import com.github.gather.repositroy.UserRepository;
import com.github.gather.repositroy.group.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ChatService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Autowired
    public ChatService(MessageRepository messageRepository, UserRepository userRepository, ChatRoomRepository chatRoomRepository, ChatParticipantRepository chatParticipantRepository, GroupMemberRepository groupMemberRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.chatParticipantRepository = chatParticipantRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    public void enterChatRoom(Long userId, Long roomId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다."));
        ChatRoom chatRoomEntity = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("채팅방을 찾을 수 없습니다."));

        GroupTable group = chatRoomEntity.getGroupId();
        Optional<GroupMember> groupMember = groupMemberRepository.findByGroupIdAndUserId(group, user);

        if (!groupMember.isPresent()) {
            throw new ErrorException("해당 그룹에 속해 있지 않은 사용자입니다.");
        }

        ChatParticipantEntity chatParticipantEntity = new ChatParticipantEntity();
        chatParticipantEntity.setJoinTime(LocalDateTime.now());
        chatParticipantEntity.setUserId(user);
        chatParticipantEntity.setRoomId(chatRoomEntity);

        chatParticipantRepository.save(chatParticipantEntity);
    }

    public void leaveChatRoom(Long userId, Long roomId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));
        ChatParticipantEntity participant = chatParticipantRepository.findByUserIdAndRoomId(user, room);

        if (participant == null) {
            throw new RuntimeException("채팅방을 찾을 수 없습니다.");
        }

        participant.setLeaveTime(LocalDateTime.now());
        chatParticipantRepository.save(participant);
    }

    public void saveMessage(MessageDto messageDto) {

        if (messageDto.getRoomId() == null || messageDto.getContent() == null || messageDto.getUserId() == null) {
            throw new IllegalArgumentException("빈 값을 넣을 수 없습니다.");
        }

        ChatRoom chatRoomEntity = chatRoomRepository.findById(messageDto.getRoomId())
                .orElseThrow(() -> new NoSuchElementException("채팅방을 찾을 수 없습니다.: " + messageDto.getRoomId()));
        User user = userRepository.findById(messageDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다.: " + messageDto.getUserId()));

        Message messageEntity = new Message(chatRoomEntity, messageDto.getContent(), new java.sql.Timestamp(System.currentTimeMillis()), user);

        messageRepository.save(messageEntity);
    }
}
