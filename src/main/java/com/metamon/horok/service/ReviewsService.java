package com.metamon.horok.service;

import com.metamon.horok.dto.WrittenReviewDTO;

import java.io.IOException;

public interface ReviewsService {
    public void writeReview(WrittenReviewDTO dto) throws IOException;
}
