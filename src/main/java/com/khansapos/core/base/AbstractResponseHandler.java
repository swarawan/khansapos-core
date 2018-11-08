package com.khansapos.core.base;

import com.khansapos.core.api.response.MetaResponse;
import com.khansapos.core.api.response.ResultResponse;
import com.khansapos.core.costant.StatusCode;
import com.khansapos.core.exception.AppException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class AbstractResponseHandler {

    public ResponseEntity<ResultResponse> getResult() {
        ResultResponse resultResponse = new ResultResponse();
        Object processResponse = processResponse();
        if (processResponse instanceof AppException) {
            resultResponse.status = StatusCode.ERROR.name();
            resultResponse.error = new MetaResponse(
                    ((AppException) processResponse).errorMessage,
                    ((AppException) processResponse).code.value());
            return generateResponseEntity(resultResponse, HttpStatus.BAD_REQUEST);
        } else {
            resultResponse.status = StatusCode.OK.name();
            resultResponse.data = processResponse;
            return generateResponseEntity(resultResponse, HttpStatus.OK);
        }
    }

    private ResponseEntity<ResultResponse> generateResponseEntity(ResultResponse result, HttpStatus code) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(result, headers, code);
    }

    protected abstract Object processResponse();
}
