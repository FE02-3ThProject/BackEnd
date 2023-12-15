package com.github.gather.exception;

public class ExceptionMessage {

    public static final String CATEGORY_NOT_FOUND = "해당 카테고리를 찾을 수 없습니다: ";
    public static final String LOCATION_NOT_FOUND = "해당 위치를 찾을 수 없습니다: ";
    public static final String GROUP_NOT_FOUND = "해당 그룹을 찾을 수 없습니다: ";
    public static final String GROUP_FULL = "그룹 멤버 수가 꽉 찼습니다: ";

    public static final String POST_NOT_FOUND_FOR_GET = "조회할 게시글 정보를 찾을 수 없습니다.";
    public static final String POST_NOT_FOUND_FOR_UPDATE = "수정할 게시글 정보를 찾을 수 없습니다.";
    public static final String POST_NOT_FOUND_FOR_DELETE = "삭제할 게시글 정보를 찾을 수 없습니다.";

    public static final String ONLY_LEADER_CAN_POST_NOTICE = "방장만이 공지를 등록할 수 있습니다.";
    public static final String NOTICE_NOT_FOUND = "공지가 존재하지 않습니다.";
    public static final String NO_AUTH_TO_MODIFY_NOTICE = "공지를 수정할 권한이 없습니다.";
    public static final String NO_AUTH_TO_DELETE_NOTICE = "공지를 삭제할 권한이 없습니다.";

}
