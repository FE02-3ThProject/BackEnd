package com.github.gather.repository;

import com.github.gather.entity.GroupTable;
import com.github.gather.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findByGroupId(GroupTable group);
}
