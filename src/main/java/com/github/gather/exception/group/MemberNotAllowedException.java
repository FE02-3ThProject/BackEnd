package com.github.gather.exception.group;

public class MemberNotAllowedException extends RuntimeException{

    public MemberNotAllowedException(){
        super("해당 멤버는 방장이 아니므로 모임을 수정할 수 없습니다.");
    }
}


