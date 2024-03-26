package com.metamon.horok.repository;

import com.metamon.horok.domain.Replies;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepliesRepository extends JpaRepository<Replies, Integer> {

    @Query("select count(r) from Replies r where r.folderReview.folderReviewId = :folderReviewId")
    public int countRepliesByFolderIdAndReviewId(@Param("folderReviewId") Integer folderReviewId);
  
    List<Replies> findByFolderReview_FolderReviewIdOrderByReplyDateAsc(Integer folderReviewId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Replies r WHERE r.folderReview.reviewId = :reviewId")
    void deleteRepliesByReviewId(@Param("reviewId") Integer reviewId);
}
