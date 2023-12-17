package com.github.gather.repositroy;

import com.github.gather.entity.Bookmark;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Bookmark findByUserIdAndGroupId(User user, GroupTable group);

}
