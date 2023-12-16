package com.github.gather.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_idx")
    private Long messageIdx;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom roomId;

    @Column(name = "content")
    private String content;

    @Column(name = "send_time")
    private Timestamp sendTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    public Message(ChatRoom roomId, String content, Timestamp sendTime, User userId) {
        this.roomId = roomId;
        this.content = content;
        this.sendTime = sendTime;
        this.userId = userId;
    }
}
