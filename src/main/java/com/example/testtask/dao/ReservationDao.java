package com.example.testtask.dao;

import com.example.testtask.exception.ProjectException;
import com.example.testtask.models.Reservation;
import com.example.testtask.models.User;

import java.util.List;

public interface ReservationDao {
    Reservation getReservationById(long id) throws ProjectException;

    Reservation save(Reservation reservation) throws ProjectException;

    List<Reservation> getReservations(String from) throws ProjectException;

    List<Reservation> getReservations(String from, User user) throws ProjectException;
}
