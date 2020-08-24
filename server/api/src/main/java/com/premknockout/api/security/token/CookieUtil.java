package com.premknockout.api.security.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

/*
 * @created 03/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Component
public class CookieUtil {

    @Value("${authentication-test.auth.accessTokenCookieName}")
    private String accessTokenCookieName;

    @Value("${authentication-test.auth.refreshTokenCookieName}")
    private String refreshTokenCookieName;

    @Value("${authentication-test.auth.isSecure}")
    private boolean isSecure;

    @Value("${authentication-test.auth.address}")
    private String domain;

    public HttpCookie createAccessTokenCookie(String token, Long duration) {
        return ResponseCookie.from(accessTokenCookieName, token)
                .maxAge(duration)
                .httpOnly(true)
                .path("/")
                .domain(domain)
                .secure(isSecure)
                .sameSite("Strict")
                .build();
    }

    public HttpCookie createRefreshTokenCookie(String token, Long duration) {
        return ResponseCookie.from(refreshTokenCookieName, token)
                .maxAge(duration)
                .httpOnly(true)
                .secure(isSecure)
                .path("/")
                .sameSite("Strict")
                .domain(domain)
                .build();
    }

}
