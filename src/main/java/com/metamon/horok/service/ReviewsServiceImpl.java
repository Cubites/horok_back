package com.metamon.horok.service;

import com.metamon.horok.domain.*;
import com.metamon.horok.dto.ReplyDTO;
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
    public void writeReview(WrittenReviewDTO dto) throws IOException {

        Users user = userRepo.findById(171).orElse(null);
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
        // .folderReviewsList(folderReviews);

        if(dto.getImages() != null){
            MultipartFile[] images = dto.getImages();
            for(int i=0; i<images.length; i++){
                if(images[i] != null && !images[i].isEmpty()){
                    String fileName = UUID.randomUUID().toString() + "_" + images[i].getOriginalFilename();
                    String filePath = path + File.separator + fileName;
                    File dest = new File(filePath);
                    images[i].transferTo(dest);
                    //String imageData = images[i];
                    switch (i) {
                        case 0:
                            reviewsBuilder.image1("/"+fileName);
                            break;
                        case 1:
                            reviewsBuilder.image2("/"+fileName);
                            break;
                        case 2:
                            reviewsBuilder.image3("/"+fileName);
                            break;
                    }
                }


            }
        }


        Reviews reviews = reviewsBuilder.build();
        reviewRepo.save(reviews);


        for(int i=0; i<foldersId.size(); i++){
//            FolderReviews fr = FolderReviews.builder().folder(folderRepo.findById(foldersId.get(i)).get()).build();
            FolderReviews fr = FolderReviews.builder().folderId(foldersId.get(i)).reviewId(reviews.getReviewId()).build();

            //folderReviews.add(fr);
            folderReviewsRepo.save(fr);
        }

//        reviewRepo.save(reviewsBuilder.folderReviewsList(folderReviews).build());
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
}
