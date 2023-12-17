package com.lab.airbnb.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.lab.airbnb.model.User;
import com.lab.airbnb.model.dao.UserDAO;
import com.lab.airbnb.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private JWTService JWTService;

    private UserDAO userDAO;

    public JWTRequestFilter(JWTService JWTService, UserDAO userDAO) {
        this.JWTService = JWTService;
        this.userDAO = userDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken token = checkToken(tokenHeader);
        if (token != null) {
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Method to authenticate a token and return the Authentication object
     * written to the spring security context.
     *
     * @param token The token to test.
     * @return The Authentication object if set.
     */
    private UsernamePasswordAuthenticationToken checkToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                String userId = JWTService.getUserId(token);
                Optional<User> opUser = userDAO.findByUserId(userId);
                if (opUser.isPresent()) {
                    User user = opUser.get();
                    if (user.getEmailVerified()) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        return authentication;
                    }
                }
            } catch (JWTDecodeException ex) {
            }
        }
        SecurityContextHolder.getContext().setAuthentication(null);
        return null;
    }
}