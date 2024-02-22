package com.metamon.horok.service;

import com.metamon.horok.repository.RepliesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepliesServiceImpl implements RepliesService{

    private final RepliesRepository repliesRepo;
    @Override
    public int countReplies(int folderReviewId){
        return repliesRepo.countRepliesByFolderIdAndReviewId(folderReviewId);
    }
}
