package com.metamon.horok.repository;

import com.metamon.horok.domain.Reviews;
import com.metamon.horok.dto.ReviewDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Transactional
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

    Integer countByUser_UserId(Integer userId);

     //MapTest 용
    @Query("SELECT r FROM Reviews r JOIN r.store s JOIN r.user u WHERE s.storeName = :name AND u.userNickname = :nname")
    public Reviews findByReviewWithStore(@Param("name") String storeName, @Param("nname") String nickname);

    //folder별 리뷰 조회
    @Query("select r from Reviews r join r.folderReviewsList fr on fr.folderId = :folderId")
    public List<Reviews> findReviewsWithFolder(@Param("folderId")Integer folderId);

    //user별 리뷰 조회
    @Query("select new com.metamon.horok.dto.ReviewDTO" +
            "(r.reviewId as reviewId, r.reviewScore as reviewScore, " +
            "r.credit as credit, r.payDate as payDate, " +
            "r.reviewDate as reviewDate, r.reviewContent as reviewContent, " +
            "r.image1 as image1, r.image2 as image2, r.image3 as image3, " +
            "r.user.userId as userId, r.store.storeName as storeName, " +
            "r.store.storeCategory as storeCategory, " +
            "r.store.latitude as latitude, r.store.longitude as longitude) " +
            "from Reviews r where r.user.userId = :userId")
    List<ReviewDTO> findReviewsByUserId(@Param("userId") Integer userId);


    //리뷰 삭제 - 내 리뷰 삭제
    @Modifying
    @Query(value = "DELETE FROM reviews WHERE review_id = :reviewId", nativeQuery = true)
    void deleteReveiw(@Param("reviewId") Integer reviewId);


    //리뷰 수정을 위한 조회
    @Query("select " +
            "new com.metamon.horok.dto.ReviewDTO(" +
            "r.reviewId as reviewId, " +
            "r.reviewScore as reviewScore, " +
            "r.reviewContent as reviewContent, " +
            "r.credit as credit, " +
            "r.payDate as payDate, " +
            "r.reviewDate as reviewDate, " +
            "r.image1 as image1, " +
            "r.image2 as image2, " +
            "r.image3 as image3, " +
            "r.store.storeName as storeName) " +
            "from Reviews r where r.reviewId = :reviewId")
    ReviewDTO findByReviewIdWithStore(@Param("reviewId") Integer reviewId);
}
