package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "reviews")
@EqualsAndHashCode(of = "reviewId")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // Review가 생성될 때 FolderReviews도 같이 생성됨 따라서 Cascade옵션 킴
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "folder_id")

    private List<FolderReviews> folderReviewsList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    /* 연관관계 편의 메서드 */
    public void setUsersReview(Users user) {
        this.user = user;
        user.getReviewsList().add(this);
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }
}