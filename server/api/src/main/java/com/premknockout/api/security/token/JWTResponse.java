package com.premknockout.api.security.token;

import java.time.LocalDateTime;

/*
 * @created 03/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
public class JWTResponse {

    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenExpiryDate;

    public JWTResponse(Token accessToken, String refreshToken) {
        this.accessToken = accessToken.getTokenValue();
        this.refreshToken = refreshToken;

        this.accessTokenExpiryDate = accessToken.getExpiryDate();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getAccessTokenExpiryDate() {
        return accessTokenExpiryDate;
    }

    public void setAccessTokenExpiryDate(LocalDateTime accessTokenExpiryDate) {
        this.accessTokenExpiryDate = accessTokenExpiryDate;

    }

}
