package com.example.testtask.services;

import com.example.testtask.dao.*;
import com.example.testtask.dto.requests.user.RegisterUserRequest;
import com.example.testtask.dto.responses.UserResponse;
import com.example.testtask.exception.ProjectErrorCode;
import com.example.testtask.exception.ProjectException;
import com.example.testtask.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserService userService;


    @BeforeEach
    void clear() {
        userRepository.deleteAll();
        reservationRepository.deleteAll();
    }

    @Test
    void registerComplete() throws ProjectException {
        assertTrue(userRepository.findAll().isEmpty());
        assertTrue(sessionRepository.findAll().isEmpty());

        final String testCookie = "COOKIES";
        final String login = "TestCookies";
        final UserResponse response = userService.register(testCookie, new RegisterUserRequest(login));
        assertEquals(login, response.getLogin());

        assertEquals(1, userRepository.findAll().size());
        assertEquals(1, sessionRepository.findAll().size());
        assertNotNull(userRepository.findAll().get(0).getSession());
    }

    @Test
    void registerBadCookie() throws ProjectException {
        try {
            userService.register(null, new RegisterUserRequest("login"));
            fail();
        } catch (final ProjectException e) {
            assertEquals(ProjectErrorCode.NO_COOKIE, e.getErrorCode());
        }
        try {
            userService.register("", new RegisterUserRequest("login"));
            fail();
        } catch (final ProjectException e) {
            assertEquals(ProjectErrorCode.NO_COOKIE, e.getErrorCode());
        }
    }

    @Test
    void registerBadRequest() throws ProjectException {
        try {
            userService.register("COOCKIE", null);
            fail();
        } catch (final ProjectException e) {
            assertEquals(ProjectErrorCode.BAD_REQUEST, e.getErrorCode());
        }
    }

    @Test
    void registerUniqueLogin() throws ProjectException {
        final String login = "login";
        userService.register("testCookie", new RegisterUserRequest(login));
        try {
            userService.register("testCookie2", new RegisterUserRequest(login));
            fail();
        } catch (final ProjectException e) {
            assertEquals(ProjectErrorCode.LOGIN_ALREADY_USED, e.getErrorCode());
        }
    }
}