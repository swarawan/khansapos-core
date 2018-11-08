package com.khansapos.core.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khansapos.core.costant.StatusCode;
import com.khansapos.core.exception.AppException;
import com.khansapos.core.payload.AccessTokenPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${security.signing-key}")
    private String securitySigningKey;

    @Value("${security.validityInMs}")
    private int validityInMs;

    @Autowired
    private ObjectMapper objectMapper;

    public String generateToken(AccessTokenPayload payload) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setSubject(payloadEncode(payload))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, securitySigningKey)
                .compact();
    }

    public AccessTokenPayload validateToken(String authToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(securitySigningKey).parseClaimsJws(authToken);

            String payloadJson = claims.getBody().getSubject();
            if (null == payloadJson || payloadJson.isEmpty()) return null;

            AccessTokenPayload payload = payloadDecode(payloadJson);
            if (null == payload) return null;
            return payload;

        } catch (SignatureException ex) {
            throw new AppException("Invalid JWT signature", StatusCode.ERROR, HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException ex) {
            throw new AppException("Invalid JWT token", StatusCode.ERROR, HttpStatus.UNAUTHORIZED);
        } catch (ExpiredJwtException ex) {
            throw new AppException("Expired JWT token", StatusCode.ERROR, HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException ex) {
            throw new AppException("Unsupported JWT token", StatusCode.ERROR, HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException ex) {
            throw new AppException("JWT claims string is empty.", StatusCode.ERROR, HttpStatus.UNAUTHORIZED);
        }
    }

    private String payloadEncode(AccessTokenPayload payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    private AccessTokenPayload payloadDecode(String payloadJson) {
        try {
            return objectMapper.readValue(payloadJson, AccessTokenPayload.class);
        } catch (IOException e) {
            return null;
        }
    }
}
