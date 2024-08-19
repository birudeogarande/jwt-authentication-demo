package com.saatvik.app.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saatvik.app.exception.RestErrorResponse;
import com.saatvik.app.exception.TokenExpiredException;
import com.saatvik.app.service.JwtService;
import com.saatvik.app.service.UserInfoService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private UserInfoService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{

        // Retrieve the Authorization header
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
            log.info(request.getPathInfo());
            log.info(request.getRequestURI());
            // Check if the header starts with "Bearer "
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7); // Extract token
                username = jwtService.extractUsername(token); // Extract username from token
            }

            // If the token is valid and no authentication is set in the context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate token and set authentication

                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }


            }

            // Continue the filter chain
            filterChain.doFilter(request, response);

        } catch (AuthenticationException | ExpiredJwtException expiredJwtException) {

            handlerExceptionResolver.resolveException(request, response, null, expiredJwtException);

        }

    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
