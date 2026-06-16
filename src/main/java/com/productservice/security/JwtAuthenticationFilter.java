package com.productservice.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = JwtUtil.retrieveTokenFromRequest(request);
        try {
            if (token == null)
                throw new AuthenticationException("Authorization header is empty");

            String email = JwtUtil.retrieveEmailFromToken(token);
            List<String> roles = JwtUtil.retrieveRolesFromToken(token);

            List<GrantedAuthority> authorities =
                    roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .map(authority ->
                                    (GrantedAuthority) authority)
                            .toList();
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            authorities
                    );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
            log.info("Authentication successful");
            log.info("Email : " + email);
            log.info("Roles : " + roles);

        } catch (AuthenticationException e) {
            log.error("Authentication failed : {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                        {
                            "message":"Authorization header is empty"
                        }
                    """);
        } catch (JWTVerificationException e) {
            log.error("Invalid JWT token : {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                        {
                            "message":"Invalid Authorization token"
                        }
                    """);
        }

        filterChain.doFilter(request, response);
    }

}
