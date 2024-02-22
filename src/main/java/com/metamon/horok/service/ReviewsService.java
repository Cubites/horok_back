package com.metamon.horok.service;

import com.metamon.horok.domain.Reviews;
import com.metamon.horok.dto.WrittenReviewDTO;

import java.io.IOException;
import java.util.List;

public interface ReviewsService {
    public void writeReview(WrittenReviewDTO dto) throws IOException;

    public List<Reviews> findReviewInFolder(Integer folderId);

//    List<Reviews> test(Integer folderId);
}
