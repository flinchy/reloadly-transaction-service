package com.chisom.transactionservice.dto.response;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class ApiResponse<T> {
    private ZonedDateTime timestamp;
    private  String message;
    private  boolean status;
    private  T data;

    public ApiResponse() {
    }

    public ApiResponse(T data, String message, boolean status) {
        timestamp = ZonedDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
