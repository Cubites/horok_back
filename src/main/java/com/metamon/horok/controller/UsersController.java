package com.metamon.horok.controller;

import com.metamon.horok.config.javaconfig.UserIdFromJwt;

import com.metamon.horok.dto.UserDTO;
import com.metamon.horok.repository.UsersRepository;
import com.metamon.horok.service.UserService;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"*"})
@RestController
@Log
public class UsersController {
    @Autowired
    private UsersRepository repo;

    private final UserService userService;
    //의존성 생성자 주입
    public UsersController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/api/users/info")
    public Optional<UserDTO> userInfo(){
        int testId = 171;
        return userService.getUserInfoByUserId(testId);
    }

//    마이페이지에 닉네임 출력 , 이미지 출력 , 카드 리스트 출력 (jwt 모르겠음,,, 일단 test 돌리는용 )
//    @GetMapping("/api/users")
//    public Optional<UserDTO> UserInfo(@RequestParam("userId") Integer userId){
//        return userService.getUserInfoByUserId(userId);
//    }

    //    마이페이지에 닉네임 출력 , 이미지 출력 , 카드 리스트 출력
    @GetMapping("/api/users")
    public Optional<UserDTO> UserInfo(@UserIdFromJwt Integer userId){
        return userService.getUserInfoByUserId(userId);
    }

    //닉네임 수정 201 페이지로 보냄 : HttpStatus.CREATED
    @PostMapping("/api/users/nickname")
    public ResponseEntity<String> UserInfoUpdate(@RequestBody UserDTO userDTO){
        userService.updateUserNickname(userDTO);
        return new ResponseEntity<>(userDTO.getUserNickname(), HttpStatus.CREATED);
    }

    //프로필 수정
    @PostMapping("/api/users/profile")
    public ResponseEntity<Integer> UserInfoUpdate2(@RequestParam("userProfile") MultipartFile userProfile, @RequestParam(name="userId")  Integer userId) throws IOException {
        System.out.println("--------"+userId);
        userService.updateUserProfile(userProfile,userId);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);

    }

    //통계 부분
    @GetMapping("/api/users/cards/status")
    public List<Object[]> getCardState(@UserIdFromJwt Integer userId) {
        return userService.findMonthlyCardUsageByCategory(userId);
    }
}