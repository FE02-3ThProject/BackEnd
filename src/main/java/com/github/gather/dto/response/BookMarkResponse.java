package com.github.gather.dto.response;

import com.github.gather.entity.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookMarkResponse {

    private Long bookmarkIdx;
    private Long userId;
    private Long groupId;
    // ... 다른 필요한 필드 추가

    // BookMark을 BookMarkResponse로 변환하는 정적 메서드
    public static BookMarkResponse from(Bookmark bookmark) {
        BookMarkResponse response = new BookMarkResponse();
        response.setBookmarkIdx(bookmark.getBookmarkIdx());
        response.setUserId(bookmark.getUserId().getUserId()); // 사용자 ID 매핑
        response.setGroupId(bookmark.getGroupTable().getGroupId()); // 그룹 ID 매핑
        // ... 다른 필드에 대한 매핑 추가

        return response;
    }

}
