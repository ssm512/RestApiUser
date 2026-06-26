package com.example.restapiuser.exception;

import org.springframework.http.HttpStatus;

// 객체에다가 상태코드를 담는 class 하나 만들고
// 메시지도 담을 수 있음
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
