package com.example.testtask.services;

import com.example.testtask.dao.ReservationDao;
import com.example.testtask.dao.UserDao;
import com.example.testtask.dto.ResponseBase;
import com.example.testtask.dto.requests.reservation.CreateReservationRequest;
import com.example.testtask.dto.responses.ListReservationResponse;
import com.example.testtask.dto.responses.ReservationResponse;
import com.example.testtask.exception.ProjectException;
import com.example.testtask.mapper.DtoMapStruct;
import com.example.testtask.models.Reservation;
import com.example.testtask.models.User;
import com.example.testtask.models.base.StatusReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = ProjectException.class)
public class ReservationService extends ValidationService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private DtoMapStruct dtoMapStruct;

    public ReservationResponse createReservation(final String cookie, final CreateReservationRequest request) throws ProjectException {
        cookieNotNullAndNotEmpty(cookie);
        requestNotNull(request);
        final User user = userDao.getUserByCookie(cookie);
        userNotNull(user);
        final Reservation reservation = dtoMapStruct.toReservation(request, user);
        reservation.setStatus(StatusReservation.ACTIVE);
        reservationDao.save(reservation);
        return dtoMapStruct.toReservationResponse(reservation);
    }

    public ReservationResponse cancelReservation(final long id, final String cookie) throws ProjectException {
        cookieNotNullAndNotEmpty(cookie);
        final Reservation reservation = reservationDao.getReservationById(id);
        reservationNotNull(reservation);
        final User user = userDao.getUserByCookie(cookie);
        userNotNull(user);
        reservationNotByUser(reservation, user);
        reservationStatusActive(reservation);
        reservation.setStatus(StatusReservation.CANCEL);
        reservationDao.save(reservation);
        return dtoMapStruct.toReservationResponse(reservation);
    }

    public ListReservationResponse getReservations(final String cookie, final String from, final boolean onlyPersonal) throws ProjectException {
        cookieNotNullAndNotEmpty(cookie);
        final User user = userDao.getUserByCookie(cookie);
        userNotNull(user);
        final List<Reservation> list;
        if (onlyPersonal) {
            list = reservationDao.getReservations(from, user);
        } else {
            list = reservationDao.getReservations(from);
        }
        final List<ReservationResponse> responses = dtoMapStruct.toReservationResponse(list);
        return dtoMapStruct.toListReservationResponse(responses);
    }
}
