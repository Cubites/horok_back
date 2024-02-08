package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "favors")
@EqualsAndHashCode(of = "favorId")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_review_id")
    private FolderReviews folderReview;

    /*연관관계 편의메서드*/
    public void setUserFavor(Users user,FolderReviews folderReview){
        this.user = user;
        this.folderReview = folderReview;
        user.getFavorsList().add(this);
        folderReview.getFavorsList().add(this);
    }
}
