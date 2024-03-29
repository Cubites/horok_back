package com.metamon.horok.service;

public interface RepliesService {

    int countReplies(int folderReviewId);
    String createReply (Integer userId, Integer folderReviewId, String replyContent);
    public String deleteReplies(Integer replyId);

    void deleteRepliesByReviewId(Integer reviewId);

}
