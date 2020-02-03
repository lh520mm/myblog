package com.h52mm.blog.exception;

public class BusinessException extends RuntimeException {

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BusinessException(String message){
        super(message);
        this.message=message;
    }

}
