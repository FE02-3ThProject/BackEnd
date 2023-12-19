package com.github.gather.repositroy;

import com.github.gather.entity.UserGroup;
import org.apache.catalina.Group;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupTableRepository extends JpaRepository<UserGroup, Long> {
}