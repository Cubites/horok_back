package com.metamon.horok.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Integer reviewId;
    private double reviewScore;
    private String reviewContent;
    private Integer credit;
    private LocalDateTime payDate;
    private LocalDateTime reviewDate;
    private String image1;
    private String image2;
    private String image3;

    private Integer userId;

    private String storeName;
    private String storeCategory;
    private Double latitude;
    private Double longitude;

    private Integer folderReviewId;

    private Integer replyCnt;
//    private Integer favorCnt;
//    private Boolean isFavor;

}
