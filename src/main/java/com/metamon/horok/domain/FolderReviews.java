package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name="folder_id")
    private Integer folderId;
    @Column(name="review_id")
    private Integer reviewId;

    @OneToMany(mappedBy = "folderReview")
    private List<Favors> favorsList;
    @OneToMany(mappedBy = "folderReview")
    private List<Replies> repliesList;

}
