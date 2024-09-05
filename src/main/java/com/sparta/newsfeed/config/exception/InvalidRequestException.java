package com.sparta.newsfeed.config.exception;

public class InvalidRequestException extends CustomException {

    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
