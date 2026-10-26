package com.hgo_soft.device_for_all.auth.filters;

import com.hgo_soft.device_for_all.auth.config.JwtAuthenticationEntryPoint;
import com.hgo_soft.device_for_all.auth.services.JwtService;
import com.hgo_soft.device_for_all.auth.services.SecurityUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SecurityUserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return "POST".equals(request.getMethod())
                && "/auth/login".equals(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");


        if(authHeader == null) {
            filterChain.doFilter(request,response);
            return;
        }

        if(!authHeader.startsWith("Bearer ")) {
            authenticationEntryPoint.sendUnauthorized(request, response, "Invalid Token!!!");
            return;
        }

        String jwt = authHeader.substring(7).trim();

        if(jwt.isEmpty()) {
            authenticationEntryPoint.sendUnauthorized(request, response, "Invalid Token!!!");
            return;

        }

        try {
            String username =jwtService.extractUsername(jwt);
            if(username == null || username.isBlank() ) {
                authenticationEntryPoint.sendUnauthorized(request, response, "Invalid Token!!!");
                return;
            }

            if(SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = userDetailsService.loadUserByUsername(username);

                if(!jwtService.isTokenValid(jwt, userDetails.getUsername())){
                    authenticationEntryPoint.sendUnauthorized(request, response, "Invalid Token!!!");
                    return;
                }

                if(!userDetails.isEnabled()) {
                    authenticationEntryPoint.sendUnauthorized(request, response, "Invalid Token!!!");
                    return;
                }

                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch(ExpiredJwtException exception) {
            authenticationEntryPoint.sendUnauthorized(request, response, "Token has expired!!!");
        } catch(JwtException | IllegalArgumentException | AuthenticationException exception) {
            authenticationEntryPoint.sendUnauthorized(request, response, "Invalid Token!!!");
        }
    }
}