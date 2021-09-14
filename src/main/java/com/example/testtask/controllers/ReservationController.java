package com.example.testtask.controllers;

import com.example.testtask.dto.requests.reservation.CreateReservationRequest;
import com.example.testtask.dto.ResponseBase;
import com.example.testtask.exception.ProjectException;
import com.example.testtask.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase createReservation(@CookieValue(value = "SESSION_ID", defaultValue = "") final String cookie,
                                          @RequestBody @Valid final CreateReservationRequest request) throws ProjectException {
        return reservationService.createReservation(cookie, request);
    }

    @PostMapping(value = "/{id}/cancel", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase cancelReservation(@PathVariable("id") final int id,
                                          @CookieValue(value = "SESSION_ID", defaultValue = "") final String cookie) throws ProjectException {
        return reservationService.cancelReservation(id, cookie);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase getReservations(@CookieValue(value = "SESSION_ID", defaultValue = "") final String cookie,
                                        @RequestParam(value = "from", required = false, defaultValue = "") final String from,
                                        @RequestParam(value = "onlyPersonal", required = false, defaultValue = "true") final boolean onlyPersonal) throws ProjectException {
        return reservationService.getReservations(cookie, from, onlyPersonal);
    }

}
