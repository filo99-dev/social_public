package org.elis.social.security.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Component
public class MyCorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var response = (HttpServletResponse) servletResponse;
        var request = (HttpServletRequest) servletRequest;
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE,"3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,HttpHeaders.AUTHORIZATION);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,HttpHeaders.CONTENT_TYPE+","+HttpHeaders.ACCEPT+","+HttpHeaders.ALLOW);
        String method = request.getMethod();
        if(method.equals(RequestMethod.OPTIONS.name()))
        {
            response.setStatus(HttpStatus.OK.value());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}