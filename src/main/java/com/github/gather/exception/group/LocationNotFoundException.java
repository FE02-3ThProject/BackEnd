package com.github.gather.exception.group;

public class LocationNotFoundException extends RuntimeException{

    public LocationNotFoundException(){
        super("해당 지역을 찾을 수 없습니다.");
    }
}


