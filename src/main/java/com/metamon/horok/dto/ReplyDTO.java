package com.metamon.horok.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReplyDTO {
    //login id
    private Integer loginId;
    //users
    private Integer userId;
    private String userNickname;
    private String userProfile;
    //replies
    private Integer replyId;
    private String replyContent;
    private LocalDateTime replyDate;

    //folderReview
    private Integer folderReviewId;

}
