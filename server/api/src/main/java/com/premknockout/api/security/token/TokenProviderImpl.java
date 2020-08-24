package com.premknockout.api.security.token;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/*
 * @created 03/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Service
public class TokenProviderImpl implements TokenProvider {

    private String tokenSecret = "peew5dn4ytooF";

    @Value("${authentication-test.auth.tokenExpirationMsec}")
    private Long tokenExpirationMsec;

    @Value("${authentication-test.auth.refreshTokenExpirationMsec}")
    private Long refreshTokenExpirationMsec;

    @Override
    public Token generateAccessToken(String subject) {
        Date now = new Date();
        Long duration = now.getTime() + tokenExpirationMsec;
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
        return new Token(Token.TokenType.ACCESS, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public Token generateRefreshToken(String subject) {
        Date now = new Date();
        long duration = now.getTime() + refreshTokenExpirationMsec;
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
        return new Token(Token.TokenType.REFRESH, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    @Override
    public LocalDateTime getExpiryDateFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    @Override
    public boolean validateToken(String token) {
        CookieUtil cookieUtil = new CookieUtil();

        try {
            Object jwt = Jwts.parser().setSigningKey(tokenSecret).parse(token);
        } catch (SignatureException ex) {
            ex.printStackTrace();
        } catch (MalformedJwtException ex) {
            throw new RuntimeException("Something is not right...");
        } catch (ExpiredJwtException ex) {
            ex.getCause();
        } catch (UnsupportedJwtException ex) {
            throw new RuntimeException("Something is not right...");
        } catch (IllegalArgumentException ex) {
            if (token != null && !token.isEmpty()) {
                throw new RuntimeException(ex.getLocalizedMessage());
            }
        }

        return true;
    }

}
