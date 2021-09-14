package com.example.testtask.dao;

import com.example.testtask.models.Reservation;
import com.example.testtask.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation,Long> {

    List<Reservation> findAll();

    Reservation findById(long id);

    List<Reservation> findByFromWhereLike(String from);

    List<Reservation> findByFromWhereLikeAndUser(String from, User user);
}
