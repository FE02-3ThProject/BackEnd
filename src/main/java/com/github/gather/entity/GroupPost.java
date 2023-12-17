package com.github.gather.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "group_post")
public class GroupPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_idx")
    private Long postIdx;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupTable groupId;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @Column(name = "email")
    private String email;

    // 게시글 수정
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}

