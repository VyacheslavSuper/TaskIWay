package com.example.testtask.exception;

import lombok.Getter;

@Getter
public class ProjectException extends Exception {
    private ProjectErrorCode errorCode;

    public ProjectException(ProjectErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}
