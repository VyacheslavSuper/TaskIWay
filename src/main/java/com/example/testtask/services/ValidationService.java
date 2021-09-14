package com.example.testtask.services;

import com.example.testtask.dto.requests.RequestBase;
import com.example.testtask.exception.ProjectErrorCode;
import com.example.testtask.exception.ProjectException;
import com.example.testtask.models.Reservation;
import com.example.testtask.models.User;
import com.example.testtask.models.base.StatusReservation;

public class ValidationService {

    protected void userNotNull(final User user) throws ProjectException {
        if (user == null) {
            throw new ProjectException(ProjectErrorCode.USER_NOT_FOUND);
        }
    }

    protected void cookieNotNullAndNotEmpty(final String cookie) throws ProjectException {
        if (cookie == null || cookie.isEmpty()) {
            throw new ProjectException(ProjectErrorCode.NO_COOKIE);
        }
    }

    protected void requestNotNull(final RequestBase request) throws ProjectException {
        if (request == null) {
            throw new ProjectException(ProjectErrorCode.BAD_REQUEST);
        }
    }

    protected void reservationNotNull(final Reservation reservation) throws ProjectException {
        if (reservation == null) {
            throw new ProjectException(ProjectErrorCode.RESERVATION_NOT_FOUND);
        }
    }

    protected void reservationStatusActive(final Reservation reservation) throws ProjectException {
        if(reservation.getStatus() != StatusReservation.ACTIVE){
            throw new ProjectException(ProjectErrorCode.NOT_ENOUGH_RIGHTS);
        }
    }

    protected void reservationNotByUser(final Reservation reservation, final User user) throws ProjectException {
        if (!reservation.getUser().equals(user)) {
            throw new ProjectException(ProjectErrorCode.NOT_ENOUGH_RIGHTS);
        }
    }
}
