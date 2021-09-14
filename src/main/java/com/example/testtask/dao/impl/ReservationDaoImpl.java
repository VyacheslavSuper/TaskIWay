package com.example.testtask.dao.impl;

import com.example.testtask.dao.ReservationDao;
import com.example.testtask.dao.ReservationRepository;
import com.example.testtask.exception.ProjectErrorCode;
import com.example.testtask.exception.ProjectException;
import com.example.testtask.models.Reservation;
import com.example.testtask.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationDaoImpl implements ReservationDao {
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation getReservationById(final long id) throws ProjectException {
        try {
            return reservationRepository.findById(id);
        } catch (final RuntimeException e) {
            throw new ProjectException(ProjectErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public Reservation save(final Reservation reservation) throws ProjectException {
        try {
            return reservationRepository.save(reservation);
        } catch (final RuntimeException e) {
            throw new ProjectException(ProjectErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<Reservation> getReservations(final String from) throws ProjectException {
        try {
            return reservationRepository.findByFromWhereLike(from);
        } catch (final RuntimeException e) {
            throw new ProjectException(ProjectErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public List<Reservation> getReservations(final String from, final User user) throws ProjectException {
        try {
            return reservationRepository.findByFromWhereLikeAndUser(from, user);
        } catch (final RuntimeException e) {
            throw new ProjectException(ProjectErrorCode.DATABASE_ERROR);
        }
    }
}
