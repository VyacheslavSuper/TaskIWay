package com.example.testtask.dao;

import com.example.testtask.models.Session;
import com.example.testtask.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, Long> {
    Session findByCookie(String cookie);

    List<Session> findAll();

    @Query("select c.user from Session c where c.cookie = :cookie")
    User findUserByCookie(@Param("cookie") String cookie);
}
