package com.project.finances.app.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CustomException {
    private String path;
//    private String method;
    private String message;
    private String title;
    private long timestamp;
    private Integer status;
}
