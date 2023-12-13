package com.github.gather.exception.group;

public class GroupNotFoundException extends RuntimeException{

    public GroupNotFoundException(){
        super("해당 모임을 찾을 수 없습니다.");
    }
}


