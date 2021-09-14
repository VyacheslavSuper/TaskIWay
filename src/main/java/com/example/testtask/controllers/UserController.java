package com.example.testtask.controllers;

import com.example.testtask.dto.requests.user.RegisterUserRequest;
import com.example.testtask.dto.ResponseBase;
import com.example.testtask.exception.ProjectException;
import com.example.testtask.services.CookieService;
import com.example.testtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CookieService cookieService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBase registration(@Valid @RequestBody final RegisterUserRequest request, final HttpServletResponse response) throws ProjectException {
        final Cookie cookie = cookieService.createCookie();
        final ResponseBase responseBase = userService.register(cookie.getValue(), request);
        response.addCookie(cookie);
        return responseBase;
    }
}
