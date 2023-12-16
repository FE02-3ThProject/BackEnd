package com.github.gather.exception.group;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("해당 회원을 찾을 수 없습니다.");
    }
}


