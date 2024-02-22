package com.metamon.horok.repository;

import com.metamon.horok.domain.Replies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepliesRepository extends JpaRepository<Replies, Integer> {
    List<Replies> findByFolderReview_FolderReviewIdOrderByReplyDateAsc(Integer folderReviewId);
}
