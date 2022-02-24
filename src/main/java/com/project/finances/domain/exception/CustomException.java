package com.project.finances.domain.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class CustomException {
    private String path;
    private List<String> fields;
    private String message;
    private Integer timestamp;
    private Integer status;

    public CustomException(String path, List<String> fields, String message, Integer timestamp, Integer status) {
        this.path = path;
        this.fields = fields;
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }
}
