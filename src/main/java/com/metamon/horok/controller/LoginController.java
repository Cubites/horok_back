package com.metamon.horok.controller;

import com.metamon.horok.config.FileStore;
import com.metamon.horok.domain.UserLoginInfo;
import com.metamon.horok.domain.Users;
import com.metamon.horok.dto.SignUpUserDto;
import com.metamon.horok.dto.UploadImageDTO;
import com.metamon.horok.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final FileStore fileStore;
    private final UsersRepository usersRepository;

    @Value("${upload.path}")
    private String fileDir;


    @PostMapping("/login/signup")
    public ResponseEntity<Map<String,String>> signUpUser(@ModelAttribute SignUpUserDto param) throws IOException {
        Users newUser=null;
        if(param.getProfile() != null) {
            UploadImageDTO uploadImageDTO = fileStore.storeFile(param.getProfile());
            newUser = Users.builder().userNickname(param.getNick())
                    .personalCode("1")
                    .userProfile(uploadImageDTO.getStoreFileName())
                    .userRegdate(LocalDateTime.now())
                    .userLoginType(param.getProvider())
                    .agreement(param.getAgreement())
                    .build();
        }else {

            log.info("param {}",param);
            newUser = Users.builder().userNickname(param.getNick())
                    .userProfile("profile.png")
                    .personalCode("1")
                    .userRegdate(LocalDateTime.now())
                    .userLoginType(param.getProvider())
                    .agreement(param.getAgreement())
                    .build();
        }
        UserLoginInfo loginInfo = UserLoginInfo.builder().userLoginProvider(param.getProvider()).
                userLoginEmail(param.getEmail()).userLoginRole("USER").build();

        newUser.addUserLoginInfo(loginInfo);
        usersRepository.save(newUser);
        Map<String,String> response = new ConcurrentHashMap<>();
        response.put("message","유저등록성공");
        response.put("status","true");


        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
