package com.github.gather.repository;

import com.github.gather.entity.ChatParticipantEntity;
import com.github.gather.entity.ChatRoom;
import com.github.gather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipantEntity, Long> {

    ChatParticipantEntity findByUserIdAndRoomId(User userId, ChatRoom roomId);

}
