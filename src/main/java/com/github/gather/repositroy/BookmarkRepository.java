package com.github.gather.repositroy;

import com.github.gather.entity.Bookmark;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Bookmark findByUserIdAndGroupId(User user, GroupTable group);

    List<Bookmark> findByUserId(User user);
}
