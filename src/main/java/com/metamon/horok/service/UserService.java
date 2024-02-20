package com.metamon.horok.service;
import com.metamon.horok.domain.Users;
import com.metamon.horok.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {
    public Optional<UserDTO> getUserInfoByUserId (Integer userId);
    public void updateUserNickname (UserDTO userDTO);

    public void updateUserProfile(MultipartFile userProfile,Integer userId) throws IOException;

    //사용자 결제내역 기반 통계
    List<Object[]> findMonthlyCardUsageByCategory(Integer userId);
//    List<Object[]> findMonthlyCardUsageByCategory(Integer userId, LocalDateTime startDate);
//List<Object[]> findMonthlyAllCardUsageByCategory(Integer userId);
}