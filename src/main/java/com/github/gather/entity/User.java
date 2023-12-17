package com.github.gather.entity;

import com.github.gather.entity.Role.UserRole;
import com.github.gather.oauth2.SocialType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location locationId;

    @ManyToOne
    @JoinColumn(name = "favorite_category")
    private Category categoryId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "image")
    private String image;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "lock_count")
    private Integer lockCount;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)




//    @OneToMany(mappedBy = "user")
//    private List<UserGroup> userGroups;    // UserGroup과 양방향 관계를 맺기 위해 생성

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    // 그룹에 참여한 그룹 목록
    @ManyToMany
    @JoinTable(
            name = "user_group_member",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<UserGroupTable> joinGroups = new HashSet<>();


    // 북마크한 그룹 목록
    @ManyToMany(mappedBy = "bookmarkedBy")
    private Set<UserGroupTable> bookmarkedGroups = new HashSet<>();


}

