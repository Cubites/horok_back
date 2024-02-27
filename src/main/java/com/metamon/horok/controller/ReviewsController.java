package com.metamon.horok.controller;


import com.metamon.horok.config.javaconfig.UserIdFromJwt;
import com.metamon.horok.domain.Reviews;
import com.metamon.horok.dto.ReviewDTO;
import com.metamon.horok.dto.ReplyDTO;
import com.metamon.horok.dto.WrittenReviewDTO;
import com.metamon.horok.repository.FolderReviewsRepository;
import com.metamon.horok.service.FavorsService;
import com.metamon.horok.service.FolderReviewsService;
import com.metamon.horok.service.RepliesService;
import com.metamon.horok.service.ReviewsService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
@Slf4j
public class ReviewsController {
    private final ReviewsService reviewsService;
    private final RepliesService repliesService;
    private final FavorsService favorsService;
    private final FolderReviewsRepository frRepo;
    private final FolderReviewsService frService;


    public ReviewsController(ReviewsService reviewsService, FolderReviewsRepository frRepo,
                             RepliesService repliesService, FavorsService favorsService,
                             FolderReviewsService frService){
        this.reviewsService = reviewsService;
        this.repliesService = repliesService;
        this.favorsService = favorsService;
        this.frRepo = frRepo;
        this.frService = frService;
    }
    @PostMapping("/api/reviews/write")
    public String writeReview(@UserIdFromJwt Integer userId,
                            @RequestParam(name = "images", required = false) MultipartFile[] images,
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
                .userId(userId)
                .credit(credit).build();

        reviewsService.writeReview(reviewDTO);

        return "true";
    }

    @GetMapping("/api/reviews/{folderId}")
    public List<ReviewDTO> getReviewListInFolder(@PathVariable("folderId") Integer folderId, @UserIdFromJwt Integer userId){
        List<Reviews> reviewList = reviewsService.findReviewInFolder(folderId);
        List<ReviewDTO> result = new ArrayList<>();

        for(Reviews review : reviewList){
            int folderReviewId = frRepo.findFolderReviewIdByFolderIdAndReviewId(folderId, review.getReviewId());

            result.add(ReviewDTO
                    .builder()
                    .reviewId(review.getReviewId())
                    .reviewScore(review.getReviewScore())
                    .reviewDate(review.getReviewDate())
                    .reviewContent(review.getReviewContent())
                    .credit(review.getCredit())
                    .payDate(review.getPayDate())
                    .image1(review.getImage1())
                    .image2(review.getImage2())
                    .image3(review.getImage3())
                    .userId(review.getUser().getUserId())
                    .storeName(review.getStore().getStoreName())
                    .storeCategory(review.getStore().getStoreCategory())
                    .latitude(review.getStore().getLatitude())
                    .longitude(review.getStore().getLongitude())
                    .folderReviewId(folderReviewId)
                    .replyCnt(repliesService.countReplies(folderReviewId))
                    .build()
            );



        }
        return result;
    }

    @PostMapping("/api/reviews/favors")
    public String addFavor(@RequestParam("folderReviewId") Integer folderReviewId, @UserIdFromJwt Integer userId){
        favorsService.addFavor(userId, folderReviewId);
        return "true";
    }

    @DeleteMapping("/api/reviews/favors/{folderReviewId}")
    public String removeFavor(@PathVariable("folderReviewId") Integer folderReviewId, @UserIdFromJwt Integer userId){
        favorsService.removeFavor(userId, folderReviewId);
        return "true";
    }

    @GetMapping("/api/reviews/replies/{folderId}/{reviewId}")
    public List<ReplyDTO> repliesList (@UserIdFromJwt Integer loginId, @PathVariable("folderId") Integer folderId, @PathVariable("reviewId") Integer reviewId){
        return reviewsService.getReplies(loginId, folderId, reviewId);
    }

    @GetMapping("/api/reviews/myreview")
    public List<ReviewDTO> getMyReviews(@UserIdFromJwt Integer userId){
        return reviewsService.getMyReviews(userId);
    }

    @DeleteMapping("/api/reviews/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") Integer reviewId){

        // 공유된 리뷰에 달린 댓글 삭제
        repliesService.deleteRepliesByReviewId(reviewId);
        // 공유된 리뷰에 달린 좋아요 삭제
        favorsService.deleteFavorByReviewId(reviewId);

        // 리뷰 삭제
        reviewsService.deleteReview(reviewId);
        // 공유된 리뷰 삭제
        frService.deleteFolderReviewByReviewId(reviewId);

    }

    // 리뷰 수정
    @PostMapping("/api/reviews/edit")
    public String editReview(@RequestParam(name = "images", required = false) MultipartFile[] images,
                      @RequestParam("reviewId") Integer reviewId,
                      @RequestParam("reviewScore") double reviewScore,
                      @RequestParam("reviewContent") String reviewContent,
                      @RequestParam("folders") String folders) throws IOException{

        WrittenReviewDTO reviewDTO = WrittenReviewDTO.builder()
                .reviewContent(reviewContent)
                .reviewScore(reviewScore)
                .foldersId(folders)
                .images(images).build();

        reviewsService.updateReview(reviewDTO, reviewId);

        return "true";
    }

    @GetMapping("/api/reviews/edit/{reviewId}")
    public ReviewDTO getEditReview(@PathVariable("reviewId") Integer reviewId){
        return reviewsService.getEditReview(reviewId);
    }

}
