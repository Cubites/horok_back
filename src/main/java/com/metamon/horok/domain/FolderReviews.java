package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name="folder_reviews")
@EqualsAndHashCode(of="folderReviewId")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer folderReviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folders folder;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Reviews review;

    @OneToMany(mappedBy = "folderReview")
    private List<Favors> favorsList = new ArrayList<>();
    @OneToMany(mappedBy = "folderReview")
    private List<Replies> repliesList = new ArrayList<>();


    /*
    연관관계 편의메서드
    * */
    public void setFoldersAndReviews(Folders folder, Reviews review){
        this.folder =folder;
        this.review = review;
        folder.getFolderList().add(this);
        review.getFolderReviewsList().add(this);
    }
}
