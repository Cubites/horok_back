package com.metamon.horok.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Integer userId;
    private String userProfile;
    private String userNickname;
    private Integer userReviewCnt;
    private Integer userFolderCnt;

    //카드 리스트
    private List<CardDTO> cardsList;
}
