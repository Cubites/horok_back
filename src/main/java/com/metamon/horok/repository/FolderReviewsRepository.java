package com.metamon.horok.repository;

import com.metamon.horok.domain.FolderReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FolderReviewsRepository extends JpaRepository<FolderReviews,Integer> {

    @Query("select fr.folderReviewId from FolderReviews fr WHERE fr.folderId = :folderId AND fr.reviewId = :reviewId")
    public int findByFolderIdAndReviewId(@Param("folderId") int folderId, @Param("reviewId")int reviewId);
}
