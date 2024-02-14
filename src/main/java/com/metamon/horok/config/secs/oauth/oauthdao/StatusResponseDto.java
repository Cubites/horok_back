package com.metamon.horok.config.secs.oauth.oauthdao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponseDto {
    private Integer status;
    private Object data;

    private String msg;

    private String accessToken;

    private boolean tokenExpired;


    public static StatusResponseDto success(){
        return StatusResponseDto.builder().status(200).build();
    }


}
