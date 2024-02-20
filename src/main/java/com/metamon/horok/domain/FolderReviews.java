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

    @OneToMany(mappedBy = "folderReview")
    private List<Favors> favorsList = new ArrayList<>();
    @OneToMany(mappedBy = "folderReview")
    private List<Replies> repliesList = new ArrayList<>();

}
