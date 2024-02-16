package com.metamon.horok.controller;

import com.metamon.horok.dto.UserDTO;
import com.metamon.horok.repository.UsersRepository;
import com.metamon.horok.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
//    @GetMapping("/api/pays/{userId}")
//    public List<Pays> payList(@PathVariable Integer userId){
//        return paysService.getPaysListByUserId(userId);
//    }

}
