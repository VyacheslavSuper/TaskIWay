package com.example.testtask.dao;

import com.example.testtask.exception.ProjectException;
import com.example.testtask.models.Session;
import com.example.testtask.models.User;

public interface UserDao {
    User getUserByCookie(String cookie) throws ProjectException;

    Session save(Session session) throws ProjectException;

    User save(User user) throws ProjectException;
}
