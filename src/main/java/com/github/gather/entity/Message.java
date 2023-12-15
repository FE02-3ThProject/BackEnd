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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "send_time")
    private Timestamp sendTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    // 생성자, 게터, 세터 등 필요한 메서드 추가
}
