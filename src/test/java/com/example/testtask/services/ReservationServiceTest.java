package com.example.testtask.services;

import com.example.testtask.dao.ReservationRepository;
import com.example.testtask.dao.SessionRepository;
import com.example.testtask.dao.UserDao;
import com.example.testtask.dao.UserRepository;
import com.example.testtask.dto.requests.reservation.CreateReservationRequest;
import com.example.testtask.dto.requests.user.RegisterUserRequest;
import com.example.testtask.dto.responses.ReservationResponse;
import com.example.testtask.dto.responses.UserResponse;
import com.example.testtask.exception.ProjectErrorCode;
import com.example.testtask.exception.ProjectException;
import com.example.testtask.models.base.StatusReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    void clear() {
        userRepository.deleteAll();
        reservationRepository.deleteAll();
    }

    @Test
    void createReservationComplete() throws ProjectException {
        assertTrue(reservationRepository.findAll().isEmpty());
        final String testCookie = "COOKIES";
        userService.register(testCookie, new RegisterUserRequest("TestLogin"));

        final CreateReservationRequest request = new CreateReservationRequest("A", Arrays.asList("B", "C"), LocalDateTime.now());
        final ReservationResponse response = reservationService.createReservation(testCookie, request);
        assertEquals("A", request.getFromWhere());
        assertEquals(Arrays.asList("B", "C"), response.getToWhereList());
        assertEquals(StatusReservation.ACTIVE, response.getStatus());
        assertEquals(1, reservationRepository.findAll().size());
        assertNotNull(reservationRepository.findAll().get(0).getUser());
        assertEquals(1, userRepository.findAll().get(0).getReservations().size());
    }

    @Test
    void createReservationBadCookie() throws ProjectException {
        final CreateReservationRequest request = new CreateReservationRequest("A", Arrays.asList("B", "C"), LocalDateTime.now());

        try {
            reservationService.createReservation(null, request);
            fail();
        } catch (final ProjectException e) {
            assertEquals(ProjectErrorCode.NO_COOKIE, e.getErrorCode());
        }
        try {
            reservationService.createReservation("", request);
            fail();
        } catch (final ProjectException e) {
            assertEquals(ProjectErrorCode.NO_COOKIE, e.getErrorCode());
        }
    }

    @Test
    void createReservationUserNotFound() throws ProjectException {
        final CreateReservationRequest request = new CreateReservationRequest("A", Arrays.asList("B", "C"), LocalDateTime.now());

        try {
            reservationService.createReservation("NOT_COOKIE", request);
            fail();
        } catch (final ProjectException e) {
            assertEquals(ProjectErrorCode.USER_NOT_FOUND, e.getErrorCode());
        }
    }

    @Test
    void cancelReservationComplete() throws ProjectException {
        final String testCookie = "COOKIES";
        userService.register(testCookie, new RegisterUserRequest("TestLogin"));

        final CreateReservationRequest request = new CreateReservationRequest("A", Arrays.asList("B", "C"), LocalDateTime.now());
        final ReservationResponse response = reservationService.createReservation(testCookie, request);
        final long idReservation = response.getId();

        final ReservationResponse cancelResponse = reservationService.cancelReservation(idReservation, testCookie);
        assertEquals(StatusReservation.CANCEL, cancelResponse.getStatus());
    }

    @Test
    void cancelReservationExceptionMultiCancel() throws ProjectException {
        final String testCookie = "COOKIES";
        userService.register(testCookie, new RegisterUserRequest("TestLogin"));

        final CreateReservationRequest request = new CreateReservationRequest("A", Arrays.asList("B", "C"), LocalDateTime.now());
        final ReservationResponse response = reservationService.createReservation(testCookie, request);
        final long idReservation = response.getId();

        try {
            reservationService.cancelReservation(idReservation, testCookie);
            reservationService.cancelReservation(idReservation, testCookie);
            fail();
        } catch (final ProjectException e) {
            assertEquals(ProjectErrorCode.NOT_ENOUGH_RIGHTS, e.getErrorCode());
        }
    }

    @Test
    void cancelReservationExceptionCancelByOtherUser() throws ProjectException {
        final String testCookie = "COOKIES";
        userService.register(testCookie, new RegisterUserRequest("TestLogin"));

        final CreateReservationRequest request = new CreateReservationRequest("A", Arrays.asList("B", "C"), LocalDateTime.now());
        final ReservationResponse response = reservationService.createReservation(testCookie, request);
        final long idReservation = response.getId();


        final String otherCookie = "OTHER_COOKIES";
        userService.register(otherCookie, new RegisterUserRequest("TestOtherLogin"));

        try {
            reservationService.cancelReservation(idReservation, otherCookie);
            fail();
        } catch (final ProjectException e) {
            assertEquals(ProjectErrorCode.NOT_ENOUGH_RIGHTS, e.getErrorCode());
        }
    }

    @Test
    void getReservationsComplete() throws ProjectException {
        for (int i = 0; i < 5; i++) {
            final String cookie = "COOKIES" + i;
            userService.register(cookie, new RegisterUserRequest("TestLogin" + i));
            for (int j = 0; j < 1; j++) {
                final CreateReservationRequest request = new CreateReservationRequest("A", Arrays.asList("B", "C"), LocalDateTime.now());
                reservationService.createReservation(cookie, request);
            }
            for (int j = 0; j < 4; j++) {
                final CreateReservationRequest request = new CreateReservationRequest("B", Arrays.asList("A", "C"), LocalDateTime.now());
                reservationService.createReservation(cookie, request);
            }
            for (int j = 0; j < 5; j++) {
                final CreateReservationRequest request = new CreateReservationRequest("C", Arrays.asList("A", "B"), LocalDateTime.now());
                reservationService.createReservation(cookie, request);
            }
        }

        assertEquals(50, reservationRepository.findAll().size());
        final String cookie = "COOKIES" + 0;
        assertEquals(4, reservationService.getReservations(cookie, "B", true).getList().size());
        assertEquals(20, reservationService.getReservations(cookie, "B", false).getList().size());
        assertEquals(0, reservationService.getReservations(cookie, "G", false).getList().size());
    }
}