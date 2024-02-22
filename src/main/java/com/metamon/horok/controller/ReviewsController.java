package com.metamon.horok.controller;


import com.metamon.horok.config.javaconfig.UserIdFromJwt;
import com.metamon.horok.domain.Reviews;
import com.metamon.horok.dto.ReviewDTO;
import com.metamon.horok.dto.WrittenReviewDTO;
import com.metamon.horok.repository.FolderReviewsRepository;
import com.metamon.horok.service.FavorsService;
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


    public ReviewsController(ReviewsService reviewsService, FolderReviewsRepository frRepo, RepliesService repliesService, FavorsService favorsService){
        this.reviewsService = reviewsService;
        this.repliesService = repliesService;
        this.favorsService = favorsService;
        this.frRepo = frRepo;
    }
    @PostMapping("/api/reviews/write")
    public String writeReview(@RequestParam(name = "images", required = false) MultipartFile[] images,
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

        return "true";
    }

    @GetMapping("/api/reviews/{folderId}")
    public List<ReviewDTO> getReviewListInFolder(@PathVariable("folderId") Integer folderId, @UserIdFromJwt Integer userId){
        List<Reviews> reviewList = reviewsService.findReviewInFolder(folderId);
        List<ReviewDTO> result = new ArrayList<>();

        for(Reviews review : reviewList){
            int folderReviewId = frRepo.findByFolderIdAndReviewId(folderId, review.getReviewId());
//            Map map = favorsService.getFavorInfo(userId, folderReviewId);
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
//                    .favorCnt((Integer)map.get("favorCnt"))
//                    .isFavor((Boolean) map.get("isFavor"))
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
//    @GetMapping("/api/reviews/review/{folderReviewId}")
//    public ReplyFavorDTO getRelyFavorInfo(@PathVariable("folderReviewId") Integer folderReviewId){
//        return ReplyFavorDTO
//                .builder()
//                .replyCnt(repliesService.countReplies(folderReviewId))
//                .favorCnt(0)
//                .build();
//
//    }

//    @GetMapping("/api/test/{folderId}")
//    public List<Reviews> test(@PathVariable("folderId") Integer folderId) {
//        return reviewsService.test(folderId);
//    }
}
