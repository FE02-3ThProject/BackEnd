package com.github.gather.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location locationId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image")
    private String image;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "lock_count")
    private Integer lockCount;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    // 생성자, 게터, 세터 등 필요한 메서드 추가
}

