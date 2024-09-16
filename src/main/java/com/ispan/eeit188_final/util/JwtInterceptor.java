package com.ispan.eeit188_final.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    @SuppressWarnings("null")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix

        // Validate the token
        if (!jwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // Extract the claims (user ID and role)
        // String userId = jwtUtil.extractUserId(token);
        // String userRole = jwtUtil.extractUserRole(token);

        // Optionally, you can add role-based access control here
        // if (!userRole.equals("Admin")) {
        // response.setStatus(HttpServletResponse.SC_FORBIDDEN); return false; }

        // Token is valid, proceed with the request
        return true;
    }
}
