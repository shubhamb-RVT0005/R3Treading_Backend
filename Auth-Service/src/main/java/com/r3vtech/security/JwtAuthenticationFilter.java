
package com.r3vtech.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.r3vtech.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // =========================
        // Get Authorization Header
        // =========================

        final String authorizationHeader =
                request.getHeader("Authorization");

        String token = null;
        String userId = null;

        // =========================
        // Check Bearer Token
        // =========================

        if (authorizationHeader != null
                && authorizationHeader.startsWith("Bearer ")) {

            token = authorizationHeader.substring(7);

            try {

                userId = jwtService.extractUserId(token);

            } catch (Exception e) {

                // Invalid JWT
                userId = null;
            }
        }

        // =========================
        // Authenticate User
        // =========================

        if (userId != null
                && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            try {

                if (!jwtService.isTokenExpired(token)) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userId,
                                    null,
                                    null
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }

            } catch (Exception e) {

                SecurityContextHolder
                        .clearContext();
            }
        }

        // =========================
        // Continue Request
        // =========================

        filterChain.doFilter(request, response);
    }
}