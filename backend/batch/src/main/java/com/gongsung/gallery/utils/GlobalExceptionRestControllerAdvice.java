package com.gongsung.gallery.utils;

import domain.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

import com.gongsung.gallery.exception.ServiceException;
import com.gongsung.gallery.utils.MessageUtils;

/**
 * Global Exception Advice in Main Application
 * treats only @RestController
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionRestControllerAdvice {

    @Autowired
    private MessageUtils messageUtils;

    @ExceptionHandler(Exception.class)
    public ResponseEntity handlerCommonException(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(
                ApiResult.with(null)
                        .statusCode(ApiResult.StatusCode.SERVER_ERROR)
                        .message(messageUtils.getMessage("common.error", new String[]{""}) + "\nInternal Server Error")
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity handleServiceException(ServiceException se) {
        log.error(se.getMessage());
        return new ResponseEntity<>(
                ApiResult.with(null)
                        .statusCode((se.getStatusCode() == null) ? ApiResult.StatusCode.SERVER_ERROR : se.getStatusCode())
                        .message(MessageUtils.getMessage(se.getMessage(), se.getMessageArgs()))
                , HttpStatus.OK);
    }
}