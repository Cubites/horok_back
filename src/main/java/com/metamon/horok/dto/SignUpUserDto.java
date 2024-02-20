package com.metamon.horok.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SignUpUserDto {

    private String email;

    private String nick;

    private String provider;

    private MultipartFile profile;

    private Boolean agreement;


}
