package com.metamon.horok.service;

import com.metamon.horok.domain.Users;
import com.metamon.horok.dto.CardDTO;
import com.metamon.horok.dto.UserDTO;
import com.metamon.horok.repository.ParticipantsRepository;
import com.metamon.horok.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImple implements UserService {
    private final UsersRepository userRepo;
    //    private final ReviewsRepository reviewsRepo;
    private final ParticipantsRepository partiRepo;

    //프로필 경로
    @Value("${upload.path}")
    private String path;

    @Override
    public Optional<UserDTO> getUserInfoByUserId(Integer userId) {
        Optional<Users> userOptional = userRepo.findById(userId);

        return userOptional.map(user -> {
            UserDTO userDTO = new UserDTO();

            //마이페이지 닉네임, 프로필 출력 
            userDTO.setUserId(user.getUserId());
            userDTO.setUserNickname(user.getUserNickname());
            userDTO.setUserProfile(user.getUserProfile());

            // 마이페이지 카드 리스트 출력
            userDTO.setCardsList(
                    user.getCardsList().stream()
                            .map(card -> CardDTO.toDto(card))
                            .collect(Collectors.toList())
            );
            return userDTO;
        });
    }

    //닉네임 수정
    @Override
    public void updateUserNickname(UserDTO userDTO) {
        userRepo.updateUserNickname(userDTO.getUserNickname(), userDTO.getUserId());
    }

//    @Override
//    public void updateUserProfile(UserDTO userDTO) {
//        userRepo.updateUserProfile(userDTO.getUserProfile(), userDTO.getUserId());
//    }

    //이미지 업로드
    @Override
    public void updateUserProfile(MultipartFile userProfile, Integer userId) throws IOException {
        String fileName = UUID.randomUUID().toString() +"_"+ userProfile.getOriginalFilename(); //동일한 사진이어도 다른 이름으로 저장가능하도록 처리 ?!
        String filePath = path + File.separator + fileName;
        File dest = new File(filePath);
        userProfile.transferTo(dest);
        userRepo.updateUserProfile(fileName,userId);
    }


    //통계
    @Override
    public List<Object[]> findMonthlyCardUsageByCategory(Integer userId){
        return userRepo.findMonthlyCardUsageByCategory(userId);
//    @Override
//    public List<Object[]> findMonthlyCardUsageByCategory(Integer userId, LocalDateTime startDate){
//        return userRepo.findMonthlyCardUsageByCategory(userId,startDate);
//
    }



}
