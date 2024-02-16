package com.metamon.horok.service;

import com.metamon.horok.domain.Users;
import com.metamon.horok.dto.UserDTO;
import com.metamon.horok.repository.ParticipantsRepository;
import com.metamon.horok.repository.ReviewsRepository;
import com.metamon.horok.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImple implements UserService {
    private final UsersRepository userRepo;
    private final ReviewsRepository reviewsRepo;
    private final ParticipantsRepository partiRepo;

    @Override
    public Optional<UserDTO> getUserInfoByUserId(Integer userId) {
        Optional<Users> userOptional = userRepo.findById(userId);


        return userOptional.map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUserNickname(user.getUserNickname());
            userDTO.setUserProfile(user.getUserProfile());

            userDTO.setUserReviewCnt(reviewsRepo.countByUser_UserId(userId));
            userDTO.setUserFolderCnt(partiRepo.countByUser_UserId(userId));
            return userDTO;
        });
    }
}
