package com.khansapos.core.exception;

import com.khansapos.core.api.response.MetaResponse;
import com.khansapos.core.api.response.ResultResponse;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        MetaResponse metaResponse = new MetaResponse(ex.getLocalizedMessage(), status.value());
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = status.name();
        resultResponse.error = metaResponse;
        return new ResponseEntity<>(resultResponse, status);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResultResponse> badRequest(AppException ex) {
        MetaResponse metaResponse = new MetaResponse(ex.getLocalizedMessage(), ex.code.value());
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = HttpStatus.BAD_REQUEST.name();
        resultResponse.error = metaResponse;
        return new ResponseEntity<>(resultResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse> generalError(Exception ex) {
        MetaResponse metaResponse = new MetaResponse(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = HttpStatus.INTERNAL_SERVER_ERROR.name();
        resultResponse.error = metaResponse;
        return new ResponseEntity<>(resultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ResultResponse> badRequest(EmptyResultDataAccessException ex) {
        MetaResponse metaResponse = new MetaResponse(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = HttpStatus.NOT_FOUND.name();
        resultResponse.error = metaResponse;
        return new ResponseEntity<>(resultResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NoSuchMessageException.class)
    public ResponseEntity<ResultResponse> badRequest(NoSuchMessageException ex) {
        MetaResponse metaResponse = new MetaResponse(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = HttpStatus.NOT_FOUND.name();
        resultResponse.error = metaResponse;
        return new ResponseEntity<>(resultResponse, HttpStatus.NOT_FOUND);
    }


}
