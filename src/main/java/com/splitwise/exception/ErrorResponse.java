package com.splitwise.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        super();
        this.message=message;
    }
}
