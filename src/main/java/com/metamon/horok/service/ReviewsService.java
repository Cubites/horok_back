package com.metamon.horok.service;

import com.metamon.horok.domain.Reviews;
import com.metamon.horok.dto.ReplyDTO;
import com.metamon.horok.dto.ReviewDTO;
import com.metamon.horok.dto.WrittenReviewDTO;

import java.io.IOException;
import java.util.List;

public interface ReviewsService {
    public void writeReview(WrittenReviewDTO dto, Integer userId) throws IOException;
    public List<Reviews> findReviewInFolder(Integer folderId);
    public List<ReplyDTO> getReplies(Integer loginId, Integer folderId, Integer reviewId);

    public List<ReviewDTO> getMyReviews(Integer userId);

    public void deleteReview(Integer reviewId);

    public void updateReview(WrittenReviewDTO dto, Integer reviewId) throws IOException;

    public ReviewDTO getEditReview(Integer reviewId);
}
