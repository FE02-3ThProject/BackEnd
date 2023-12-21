package com.github.gather.repository;

import com.github.gather.entity.GroupPost;
import com.github.gather.entity.GroupTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupPostRepository extends JpaRepository<GroupPost, Long> {

    List<GroupPost> findByGroupId(GroupTable group);
}
