package com.example.testtask.dao.impl;

import com.example.testtask.dao.SessionRepository;
import com.example.testtask.dao.UserDao;
import com.example.testtask.dao.UserRepository;
import com.example.testtask.exception.ProjectErrorCode;
import com.example.testtask.exception.ProjectException;
import com.example.testtask.models.Session;
import com.example.testtask.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByCookie(final String cookie) throws ProjectException {
        try {
            return sessionRepository.findUserByCookie(cookie);
        } catch (final RuntimeException e) {
            throw new ProjectException(ProjectErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public Session save(final Session session) throws ProjectException {
        try {
            return sessionRepository.save(session);
        } catch (final RuntimeException e) {
            throw new ProjectException(ProjectErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public User save(final User user) throws ProjectException {
        try {
            return userRepository.save(user);
        } catch (final DataIntegrityViolationException e) {
            throw new ProjectException(ProjectErrorCode.LOGIN_ALREADY_USED);
        } catch (final RuntimeException e) {
            throw new ProjectException(ProjectErrorCode.DATABASE_ERROR);
        }
    }
}
