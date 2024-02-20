package com.metamon.horok.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class WrittenReviewDTO {

    private double reviewScore;
    private String reviewContent;
    private Integer credit;
    private LocalDateTime payDate;
    private LocalDateTime reviewDate;
    private MultipartFile[] images;
    private Integer storeId;
    private String storeName;
    private String foldersId;
    private Integer userId;

}
