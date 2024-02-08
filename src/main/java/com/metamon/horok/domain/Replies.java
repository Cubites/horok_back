package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "replies")
@EqualsAndHashCode(of = "replyId")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Replies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer replyId;
    private String replyContent;
    private LocalDateTime replyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_review_id")
    private FolderReviews folderReview;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    public void setUserReplies(Users user, FolderReviews folderReview){
        this.user = user;
        this.folderReview = folderReview;
        user.getRepliesList().add(this);
        folderReview.getRepliesList().add(this);
    }
}
