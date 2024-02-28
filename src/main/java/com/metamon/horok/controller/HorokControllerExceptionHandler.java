package com.metamon.horok.controller;

import com.metamon.horok.config.secs.oauth.oauthdao.StatusResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class HorokControllerExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class, NumberFormatException.class})
    public StatusResponseDto exHandler(Exception e){

        log.error("[exceptionHandle] ex: 에러 발생",e);
        StatusResponseDto statusResponseDto = StatusResponseDto.builder().msg(e.getMessage()).status(500).build();

        return statusResponseDto;
    }


}
