package com.github.gather.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_idx")
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupTable groupId;

    public ChatRoom(GroupTable groupId) {
        this.groupId = groupId;
    }
}

