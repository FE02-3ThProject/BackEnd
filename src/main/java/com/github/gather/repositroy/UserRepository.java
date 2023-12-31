package com.github.gather.repositroy;


import com.github.gather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.email =:email")
    UserDetails findUserDetailByEmail(@Param("email") String email);

    @Transactional
    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.email =:email")
    Optional<User> findByEmail(@Param("email") String email);

    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);
}
