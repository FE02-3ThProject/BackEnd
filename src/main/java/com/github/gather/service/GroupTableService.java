package com.github.gather.service;

import com.github.gather.entity.GroupTable;
import com.github.gather.exception.ExceptionMessage;
import com.github.gather.repositroy.GroupTableRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupTableService {

    private final GroupTableRepository groupTableRepository;

    public GroupTableService(GroupTableRepository groupTableRepository) {
        this.groupTableRepository = groupTableRepository;
    }

    public GroupTable getGroupById(Long groupId) {
        return groupTableRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.GROUP_NOT_FOUND));
    }
}
