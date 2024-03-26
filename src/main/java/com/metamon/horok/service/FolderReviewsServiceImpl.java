package com.metamon.horok.service;

import com.metamon.horok.repository.FolderReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderReviewsServiceImpl implements FolderReviewsService{

    private final FolderReviewsRepository folderReviewsRepository;
    @Override
    public void deleteFolderReviewByReviewId(Integer reviewId) {
        folderReviewsRepository.deleteFolderReviewByReviewId(reviewId);

    }
}
