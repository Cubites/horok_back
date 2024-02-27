package com.metamon.horok.service;

import com.metamon.horok.domain.*;
import com.metamon.horok.dto.ReplyDTO;
import com.metamon.horok.dto.ReviewDTO;
import com.metamon.horok.dto.WrittenReviewDTO;
import com.metamon.horok.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewsServiceImpl implements ReviewsService{
    private final UsersRepository userRepo;
    private final StoresRepository storeRepo;
    private final ReviewsRepository reviewRepo;
    private final FolderReviewsRepository folderReviewsRepo;

    private final RepliesRepository replyRepo;

    @Value("${upload.path}")
    private String path;

    @Override
    public void writeReview(WrittenReviewDTO dto, Integer userId) throws IOException {

        Users user = userRepo.findById(userId).orElse(null);
        Stores store = storeRepo.findById(dto.getStoreId()).orElse(null);

        List<Integer> foldersId = Arrays
                .stream(dto.getFoldersId().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());



        Reviews.ReviewsBuilder reviewsBuilder =  Reviews.builder()
                .reviewScore(dto.getReviewScore())
                .reviewContent(dto.getReviewContent())
                .credit(dto.getCredit())
                .payDate(dto.getPayDate())
                .reviewDate(dto.getReviewDate())
                .user(user)
                .store(store);

        if(dto.getImages() != null){
            MultipartFile[] images = dto.getImages();
            for(int i=0; i<images.length; i++){
                if(images[i] != null && !images[i].isEmpty()){
                    String fileName = UUID.randomUUID().toString() + "_" + images[i].getOriginalFilename();
                    String filePath = path + fileName;
                    File dest = new File(filePath);
                    images[i].transferTo(dest);

                    switch (i) {
                        case 0:
                            reviewsBuilder.image1(fileName);
                            break;
                        case 1:
                            reviewsBuilder.image2(fileName);
                            break;
                        case 2:
                            reviewsBuilder.image3(fileName);
                            break;
                    }
                }


            }
        }


        Reviews reviews = reviewsBuilder.build();
        reviewRepo.save(reviews);

        if(!foldersId.get(0).equals(0)){//선택하지 않음을 선택하면 폴더 공유하지 않음
            for(int i=0; i<foldersId.size(); i++){
                FolderReviews fr = FolderReviews.builder().folderId(foldersId.get(i)).reviewId(reviews.getReviewId()).build();

                folderReviewsRepo.save(fr);
            }
        }

    }

    @Override
    public List<Reviews> findReviewInFolder(Integer folderId){
        return reviewRepo.findReviewsWithFolder(folderId);
    }

    public List<ReplyDTO> getReplies(Integer loginId,Integer folderId, Integer reviewId) {
        //폴더 리뷰 아이디 조회
        FolderReviews fr = folderReviewsRepo.findByFolderIdAndReviewId(folderId, reviewId);
        //리뷰 조회
        List<Replies> repliesList = replyRepo.findByFolderReview_FolderReviewIdOrderByReplyDateAsc(fr.getFolderReviewId());

        List<ReplyDTO> dtoList = new ArrayList<>();
        for (Replies reply : repliesList) {
            Users u = userRepo.findById(reply.getUser().getUserId()).orElse(null);
            ReplyDTO dto = new ReplyDTO();

            dto.setUserId(u.getUserId());
            dto.setUserNickname(u.getUserNickname());
            dto.setUserProfile(u.getUserProfile());
            dto.setReplyId(reply.getReplyId());
            dto.setReplyContent(reply.getReplyContent());
            dto.setReplyDate(reply.getReplyDate());
            dto.setFolderReviewId(fr.getFolderReviewId());
            dto.setLoginId(loginId);

            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<ReviewDTO> getMyReviews(Integer userId) {
        return reviewRepo.findReviewsByUserId(userId);
    }

    @Override
    public void deleteReview(Integer reviewId) {
        reviewRepo.deleteReveiw(reviewId);
    }

    @Override
    public void updateReview(WrittenReviewDTO dto, Integer reviewId) throws IOException {
        // 수정할 리뷰를 찾습니다.
        Reviews review = reviewRepo.findById(reviewId).orElse(null);
        if (review == null) {
            throw new RuntimeException("리뷰를 찾을 수 없습니다.");
        }

        // 이미지를 수정하거나 추가합니다.
        if (dto.getImages() != null) {
            MultipartFile[] images = dto.getImages();
            for (int i = 0; i < images.length; i++) {
                if (images[i] != null && !images[i].isEmpty()) {
                    String fileName = UUID.randomUUID().toString() + "_" + images[i].getOriginalFilename();
                    String filePath = path + fileName;
                    File dest = new File(filePath);
                    images[i].transferTo(dest);
                    // 이미지가 새로 추가되거나 수정되었을 경우에만 이미지 경로를 업데이트합니다.
                    switch (i) {
                        case 0:
                            review.setImage1(fileName);
                            break;
                        case 1:
                            review.setImage2(fileName);
                            break;
                        case 2:
                            review.setImage3(fileName);
                            break;
                    }
                }
            }
        }

        // 평점과 리뷰 내용을 업데이트합니다.
        review.setReviewContent(dto.getReviewContent());
        review.setReviewScore(dto.getReviewScore());


        // 선택된 폴더를 업데이트합니다.
        List<Integer> foldersId = Arrays
                .stream(dto.getFoldersId().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for (int i = 0; i < foldersId.size(); i++) { //새로 공유하는 폴더 추가
            FolderReviews fr = FolderReviews.builder().folderId(foldersId.get(i)).reviewId(reviewId).build();
            folderReviewsRepo.save(fr);
        }

        // 리뷰를 업데이트합니다.
        reviewRepo.save(review);
    }

    @Override
    public ReviewDTO getEditReview(Integer reviewId) {
        return reviewRepo.findByReviewIdWithStore(reviewId);
    }
}
