package com.metamon.horok.service;

import com.metamon.horok.domain.FolderReviews;
import com.metamon.horok.domain.Replies;
import com.metamon.horok.domain.Users;
import com.metamon.horok.repository.FolderReviewsRepository;
import com.metamon.horok.repository.RepliesRepository;
import com.metamon.horok.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor

public class RepliesServiceImpl implements RepliesService{

    private final RepliesRepository repliesRepo;
    private final UsersRepository userRepo;
    private final FolderReviewsRepository folderReviewsRepo;
    private final RepliesRepository replyRepo;
    
    @Override
    public int countReplies(int folderReviewId) {
        return repliesRepo.countRepliesByFolderIdAndReviewId(folderReviewId);
    }
    @Override
    public String createReply(Integer userId, Integer folderReviewId, String replyContent) {
        Users u = userRepo.findById(userId).orElse(null);
        FolderReviews fr = folderReviewsRepo.findById(folderReviewId).orElse(null);
        Replies r = Replies.builder().user(u).folderReview(fr).replyContent(replyContent).build();

        Replies saveReply = replyRepo.save(r);

        if (saveReply != null ) {
            return "true";  // 저장 성공
        } else {
            return "false";  // 저장 실패
        }
    }

    @Override
    public String deleteReplies(Integer replyId) {
        try {
            replyRepo.deleteById(replyId);
            System.out.println("삭제 성공");
            return "true";
        } catch (EmptyResultDataAccessException e) {
            System.out.println("삭제 실패: 해당 ID의 엔티티가 존재하지 않음");
            return "false";
        }
    }
}
