package com.github.gather.repository;

import com.github.gather.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupTableRepository extends JpaRepository<UserGroup, Long> {
}