package com.github.gather.repositroy.group;

import com.github.gather.entity.GroupTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupTable,Long> {

}
