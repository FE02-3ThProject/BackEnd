package com.github.gather.repositroy;


import com.github.gather.entity.User;
import com.github.gather.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    List<UserGroup> findByUser(User user);
    List<UserGroup> findByBookmarkedUsersContaining(User user);

    @Modifying
    @Transactional
    @Query("UPDATE UserGroup ug SET ug.bookmarkedUsers = null WHERE :user MEMBER OF ug.bookmarkedUsers AND ug.group.groupId = :groupId")
    void deleteByBookmarkedUsersContainingAndGroupGroupId(@Param("user") User user, @Param("groupId") Long groupId);
}

