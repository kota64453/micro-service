package com.productservice.security;

import jakarta.servlet.http.HttpServletRequest;
import org.bouncycastle.crypto.threshold.ShamirSecretSplitter;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class  JwtUtil {
    public static String retrieveTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
    private DecodedJWT getDecodedToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));
        return JWT.require(algorithm).build().verify(token);
    }

    public static String retrieveEmailFromToken(String token)  throws JWTVerificationException{
        log.info("Decoded JWT token: {}", getDecodedToken(token));
        return getDecodedToken(token).getSubject();
    }
    }


    public static List<String> retrieveRolesFromToken(String token) throws JWTVerificationException {
        return getDecodedToken(token).getClaim(ROLE_TAG).asList(String.class);
    }

