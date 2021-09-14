package com.example.testtask.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ProjectErrorCode {
    UNKNOWN_ERROR("Unknown error"),
    DATABASE_ERROR("Internal Server Error"),
    BAD_REQUEST("Bad request"),
    NO_COOKIE("You do not have cookies"),
    USER_NOT_FOUND("User not found"),
    NOT_ENOUGH_RIGHTS("You don't have the rights to do this"),
    RESERVATION_NOT_FOUND("Reservation not found"),
    LOGIN_ALREADY_USED("Login already used"),
    INTERNAL_SERVER_ERROR("Internal server error");

    private String message;

}
