package com.premknockout.api.security.token;

import java.time.LocalDateTime;

/*
 * @created 03/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
public interface TokenProvider {

    Token generateAccessToken(String subject);

    Token generateRefreshToken(String subject);

    String getUsernameFromToken(String token);

    LocalDateTime getExpiryDateFromToken(String token);

    boolean validateToken(String token);

}
