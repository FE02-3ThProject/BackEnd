package com.github.gather.repositroy;

import com.github.gather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEditRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
