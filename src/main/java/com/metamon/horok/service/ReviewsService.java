package com.metamon.horok.service;

import com.metamon.horok.dto.ReplyDTO;
import com.metamon.horok.dto.WrittenReviewDTO;

import java.io.IOException;
import java.util.List;

public interface ReviewsService {
    public void writeReview(WrittenReviewDTO dto) throws IOException;
    public List<ReplyDTO> getReplies(Integer loginId, Integer folderId, Integer reviewId);

}
