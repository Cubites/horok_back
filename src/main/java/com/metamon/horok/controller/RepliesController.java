package com.metamon.horok.controller;


import com.metamon.horok.config.javaconfig.UserIdFromJwt;
import com.metamon.horok.service.RepliesService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Transactional
public class RepliesController {
    private final RepliesService repliesService;

    public RepliesController(RepliesService repliesService){
        this.repliesService = repliesService;
    }
    @PostMapping("/api/replies/write")
    public String writeReply(@UserIdFromJwt Integer userId,
                            @RequestParam("folderReviewId") Integer folderReviewId,
                            @RequestParam("replyContent") String replyContent) throws IOException {

        return repliesService.createReply(userId, folderReviewId,replyContent);
    }

    @DeleteMapping("/api/replies/{replyId}")
    public String deleteReply(@PathVariable("replyId") Integer replyId){
        return repliesService.deleteReplies(replyId);
    }


}
