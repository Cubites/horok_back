package com.metamon.horok.repository;

import com.metamon.horok.domain.FolderReviews;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FolderReviewsRepository extends JpaRepository<FolderReviews,Integer> {

    @Query("select fr.folderReviewId from FolderReviews fr WHERE fr.folderId = :folderId AND fr.reviewId = :reviewId")
    public int findFolderReviewIdByFolderIdAndReviewId(@Param("folderId") int folderId, @Param("reviewId")int reviewId);
  
    FolderReviews findByFolderIdAndReviewId(Integer folderId, Integer reviewId);

    //리뷰가 삭제됨에 따라 공유된 리뷰도 삭제됨
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM folder_reviews WHERE review_id = :reviewId", nativeQuery = true)
    void deleteFolderReviewByReviewId(@Param("reviewId") Integer reviewId);
}
