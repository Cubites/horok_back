package com.metamon.horok.controller;

import com.metamon.horok.config.javaconfig.UserIdFromJwt;
import com.metamon.horok.dto.UserDTO;
import com.metamon.horok.mapper.MapMapper;
import com.metamon.horok.repository.UsersRepository;
import com.metamon.horok.service.UserService;
import com.metamon.horok.vo.MapReviewVO;
import jakarta.servlet.http.Cookie;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = { "*" })
@RestController
@Log
public class UsersController {
    @Autowired
    private UsersRepository repo;

    @Autowired
    private MapMapper mapMapper;

    private final UserService userService;

    // 의존성 생성자 주입
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/info")
    public Optional<UserDTO> userInfo( @UserIdFromJwt Integer userId,@CookieValue(value = "Authorization",required = false) String token){
        System.out.println("cookie = 아아아" + token);

        System.out.println(" **********************************************");
        System.out.println("userId = " + userId);
        System.out.println(" **********************************************");
        return userService.getUserInfoByUserId(userId);
    }

    @PostMapping("/api/users/info")
    public Optional<UserDTO> userInfo2() {
        int testId = 171;
        return userService.getUserInfoByUserId(testId);
    }

    // 마이페이지에 닉네임 출력 , 이미지 출력 , 카드 리스트 출력 ( 사용자 정보 조회 컨트롤러 )
    @GetMapping("/api/users")
    public Optional<UserDTO> UserInfo(@UserIdFromJwt Integer userId) {
        return userService.getUserInfoByUserId(userId);
    }

    // 닉네임 수정 201 페이지로 보냄 : HttpStatus.CREATED
    @PostMapping("/api/users/nickname")
    public ResponseEntity<String> UserInfoUpdate(@RequestBody UserDTO userDTO) {
        userService.updateUserNickname(userDTO);
        return new ResponseEntity<>(userDTO.getUserNickname(), HttpStatus.CREATED);
    }

    // 프로필 수정
    @PostMapping("/api/users/profile")
    public ResponseEntity<Integer> UserInfoUpdate2(@RequestParam("userProfile") MultipartFile userProfile,
            @RequestParam(name = "userId") Integer userId) throws IOException {
        System.out.println("--------" + userId);
        userService.updateUserProfile(userProfile, userId);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    // 지도에 표시할 리뷰 조회 컨트롤러
    @PostMapping("/api/users/reviews")
    public List<MapReviewVO> MapReview(@UserIdFromJwt Integer userId) {
        return mapMapper.readAllReviewFromUserId(userId);
    }

    //월간 통계 컨트롤러
    @GetMapping("/api/users/cards/status")
    public List<Object[]> getCardState(@UserIdFromJwt Integer userId,@RequestParam(name="cardNumber",required = false) List<String> cardNumber) {
        return userService.findMonthlyCardUsageByCategory(userId,cardNumber);
    }

    //연간 통계 컨트롤러
    @GetMapping("/api/users/cards/status2")
    public List<Object[]> getCardYearState(@UserIdFromJwt Integer userId,@RequestParam(name="cardNumber",required = false) List<String> cardNumber) {
        return userService.findYearlyCardUsageByCategory(userId,cardNumber);
    }
}