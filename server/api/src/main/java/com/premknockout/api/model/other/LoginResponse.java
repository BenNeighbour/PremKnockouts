package com.premknockout.api.model.other;

import com.premknockout.api.security.token.JWTResponse;

/*
 * @created 03/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
public class LoginResponse {

    private JWTResponse data;

    public LoginResponse(JWTResponse data) {
        this.data = data;
    }


    public JWTResponse getData() {
        return data;
    }

    public void setData(JWTResponse data) {
        this.data = data;
    }

}
