package com.metamon.horok.service;

import com.metamon.horok.domain.Users;
import com.metamon.horok.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    //사용자 정보 조회
    public Optional<UserDTO> getUserInfoByUserId(Integer userId);

    //닉네임 수정
    public void updateUserNickname(UserDTO userDTO);
    //이미지 수정
    public void updateUserProfile(MultipartFile userProfile, Integer userId) throws IOException;

    // 사용자 결제내역 기반 통계(월간/연간)
    List<Object[]> findMonthlyCardUsageByCategory(Integer userId,List<String> cardNumber);
    List<Object[]> findYearlyCardUsageByCategory(Integer userId,List<String> cardNumber);


}
