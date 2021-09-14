package com.example.testtask.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.UUID;

@Service
public class CookieService {
    private String generateCookie() {
        return UUID.randomUUID().toString();
    }

    public Cookie createCookie() {
        final Cookie cookie = new Cookie("SESSION_ID", generateCookie());
        cookie.setMaxAge(60 * 60);
        return cookie;
    }

    public Cookie deleteCookie() {
        final Cookie cookie = new Cookie("SESSION_ID", null);
        cookie.setMaxAge(0);
        return cookie;
    }
}
