package com.github.gather.repositroy;

import com.github.gather.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    List<UserGroup> findByUserEmail(String userEmail);
}