package com.metamon.horok.controller;

import com.metamon.horok.domain.UserLoginInfo;
import com.metamon.horok.domain.Users;
import com.metamon.horok.dto.SignUpUserDto;
import com.metamon.horok.repository.UserLoginInfoRepository;
import com.metamon.horok.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/login/signup")
    public ResponseEntity<Map<String,String>> signUpUser(@ModelAttribute SignUpUserDto param) throws IOException {
            Users newUser=null;
            if(param.getProfile() != null) {
                UploadImage uploadImage = fileStore.storeFile(param.getProfile());
               newUser = Users.builder().userNickname(param.getNick())
                        .personalCode("1")
                        .userProfile(uploadImage.getFullPath())
                        .userRegdate(LocalDateTime.now())
                        .userLoginType(param.getProvider())
                        .agreement(param.getAgreement())
                        .build();
            }else {
                log.info("여기 도착");
                log.info("param {}",param);
                newUser = Users.builder().userNickname(param.getNick())
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
