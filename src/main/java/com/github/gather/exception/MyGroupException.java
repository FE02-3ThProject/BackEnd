package com.github.gather.exception;

public class MyGroupException extends RuntimeException{

    public MyGroupException(String message){
        super(message);
    }

    public MyGroupException(String message, Throwable cause) {
        super(message, cause);
    }
}
