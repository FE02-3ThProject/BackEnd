package com.github.gather.repositroy.group;

import com.github.gather.entity.GroupTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupTable,Long> {

    @Query("SELECT g FROM GroupTable g WHERE g.categoryId.categoryId = :categoryId")
    List<GroupTable> findByCategoryId(Long categoryId);

    @Query("SELECT g FROM GroupTable g WHERE g.locationId.locationId = :locationId")
    List<GroupTable> findByLocationId(Long locationId);

    @Query("SELECT g FROM GroupTable g WHERE g.title LIKE CONCAT('%', :title, '%')")
    List<GroupTable> findByTitleContaining(String title);

    @Query("SELECT g FROM GroupTable g")
    List<GroupTable> searchAllGroups();


}
