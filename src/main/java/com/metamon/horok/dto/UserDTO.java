package com.metamon.horok.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Integer userId;

    private String userProfile;
    private String userNickname;
    private Integer userReviewCnt;
    private Integer userFolderCnt;


}
