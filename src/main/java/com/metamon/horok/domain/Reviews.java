package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name="reviews")
@EqualsAndHashCode(of="reviewId")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reviews {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer reviewId;
    private double reviewScore;
    private String reviewContent;
    private Integer credit;
    private LocalDateTime payDate;
    private LocalDateTime reviewDate;
    private String image1;
    private String image2;
    private String image3;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Stores store;

    //Review가 생성될 때 FolderReviews도 같이 생성됨 따라서 Cascade옵션 킴
    @OneToMany(mappedBy = "review",cascade = CascadeType.ALL)
    private List<FolderReviews> folderReviewsList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    /*연관관계 편의 메서드*/
    public void setUsersReview(Users user){
        this.user = user;
        user.getReviewsList().add(this);
    }
}
