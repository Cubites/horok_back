package com.metamon.horok.repository;

import com.metamon.horok.domain.Replies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RepliesRepository extends JpaRepository<Replies, Integer> {

    @Query("select count(r) from Replies r where r.folderReview.folderReviewId = :folderReviewId")
    public int countRepliesByFolderIdAndReviewId(@Param("folderReviewId") Integer folderReviewId);
  
    List<Replies> findByFolderReview_FolderReviewIdOrderByReplyDateAsc(Integer folderReviewId);
}
