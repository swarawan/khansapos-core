package com.khansapos.core.base;

import com.khansapos.core.costant.StatusCode;
import com.khansapos.core.exception.AppException;
import com.khansapos.core.payload.AccessTokenPayload;
import com.khansapos.core.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.List;

public class BaseController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    protected AbstractResponseHandler abstractResponseHandler(Object object) {
        return new AbstractResponseHandler() {
            @Override
            protected Object processResponse() {
                return object;
            }
        };
    }

    protected AccessTokenPayload parsePayload(HttpHeaders headers) {
        List<String> authorizations = headers.get("Authorization");
        if (null == authorizations)
            throw new AppException("Invalid JWT token", StatusCode.ERROR, HttpStatus.UNAUTHORIZED);

        String authorization = authorizations.get(0);
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            String authToken = authorization.substring(7);
            return tokenProvider.validateToken(authToken);
        }
        return null;
    }
}