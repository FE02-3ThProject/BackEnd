package com.github.gather.repositroy;

import com.github.gather.entity.RefreshToken;
import com.github.gather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    void deleteByExpiryDateBefore(LocalDateTime expiryDate);

    Optional<RefreshToken> findByUser(User user);
    Optional<RefreshToken> findFirstByUserOrderByExpiryDateDesc(User user);



    Optional<RefreshToken> findFirstByUser_UserIdOrderByExpiryDateDesc(Long userId);
}
