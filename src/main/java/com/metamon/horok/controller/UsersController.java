package com.metamon.horok.controller;

import com.metamon.horok.dto.UserDTO;
import com.metamon.horok.repository.UsersRepository;
import com.metamon.horok.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

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

    //마이페이지에 닉네임 출력 , 이미지 출력 , 카드 리스트 출력
    @GetMapping("/api/users")
    public Optional<UserDTO> UserInfo(@RequestParam("userId") Integer userId){
        return userService.getUserInfoByUserId(userId);
    }



}