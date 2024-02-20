package com.metamon.horok.controller;


import com.metamon.horok.dto.WrittenReviewDTO;
import com.metamon.horok.service.ReviewsService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@Transactional
public class ReviewsController {
    private final ReviewsService reviewsService;


    public ReviewsController(ReviewsService reviewsService){
        this.reviewsService = reviewsService;
    }
    @PostMapping("/api/reviews/write")
    public void writeReview(@RequestParam(name = "images", required = false) MultipartFile[] images,
                            @RequestParam("storeName") String storeName,
                            @RequestParam("payDate") String payDate,
                            @RequestParam("credit") int credit,
                            @RequestParam("reviewScore") double reviewScore,
                            @RequestParam("reviewContent") String reviewContent,
                            @RequestParam("folders") String folders,
                            @RequestParam("storeId") int storeId) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(payDate, formatter);
        WrittenReviewDTO reviewDTO = WrittenReviewDTO.builder()
                .reviewDate(LocalDateTime.now())
                .reviewContent(reviewContent)
                .storeId(storeId)
                .storeName(storeName)
                .payDate(dateTime)
                .reviewScore(reviewScore)
                .foldersId(folders)
                .images(images)
                .userId(171)
                .credit(credit).build();

        reviewsService.writeReview(reviewDTO);
    }
}
