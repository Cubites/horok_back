package com.metamon.horok.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MapReviewVO {
    private Integer reviewId;
    private Integer storeId;
    private Integer folderId;
    private String userNickname;
    private String folderName;
    private String storeName;
    private String storeCategory;
    private Double latitude;
    private Double longitude;
    private String image1;
    private Double reviewScore;
    private String reviewContent;
    private String payDate;
}
