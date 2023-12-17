package com.github.gather.service;

import com.github.gather.entity.Bookmark;
import com.github.gather.entity.GroupTable;
import com.github.gather.entity.User;
import com.github.gather.repositroy.BookmarkRepository;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public void toggleBookmark(User user, GroupTable group) {
        Bookmark existingBookmark = bookmarkRepository.findByUserIdAndGroupId(user, group);

        if (existingBookmark != null) {
            bookmarkRepository.delete(existingBookmark);
        } else {
            Bookmark bookmark = new Bookmark(null, user, group);
            bookmarkRepository.save(bookmark);
        }
    }
}

