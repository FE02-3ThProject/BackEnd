package com.github.gather.repositroy;

import com.github.gather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<User,Long> { }
