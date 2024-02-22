package com.metamon.horok.service;

public interface RepliesService {
    String createReply (Integer userId, Integer folderReviewId, String replyContent);
    public String deleteReplies(Integer replyId);
}
