package com.premknockout.api.security.token;

import com.premknockout.api.service.securityUser.SecurityUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @created 03/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${authentication-test.auth.accessTokenCookieName}")
    private String accessTokenCookieName;

    @Value("${authentication-test.auth.refreshTokenCookieName}")
    private String refreshTokenCookieName;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private SecurityUserServiceImpl securityUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!httpServletRequest.getServletPath().equals("/auth/login")) {
                String jwt = getJwtToken(httpServletRequest, true);

                if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                    String username = tokenProvider.getUsernameFromToken(jwt);
                    UserDetails userDetails = securityUserService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            ex.getMessage();
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String accessToken = bearerToken.substring(7);
            if (accessToken == null) return null;

            return accessToken;
        }
        return getJwtFromCookie(request);
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (accessTokenCookieName.equals(cookie.getName())) {
                    String accessToken = cookie.getValue();
                    if (accessToken == null) return null;

                    return accessToken;
                }
            }
        }

        return null;
    }

    private String getJwtToken(HttpServletRequest request, boolean fromCookie) {
        if (fromCookie) return getJwtFromCookie(request);

        return getJwtFromRequest(request);
    }

}
