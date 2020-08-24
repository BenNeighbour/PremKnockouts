package com.premknockout.api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @created 04/07/2020 - 21
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticatonFilter implements Filter {

    @Value("${authentication-test.auth.fullAddress}")
    private String domain;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", domain);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH, HEAD");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, X-Requested-With, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Origin, Authorization, Cookie, X-XSRF-TOKEN");
        response.setHeader("Max-Age", "3000");
        response.setHeader("Access-Control-Expose-Headers", "Set-Cookie");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        filterChain.doFilter(request, servletResponse);
    }
}
