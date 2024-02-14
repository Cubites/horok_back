package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "users")
@EqualsAndHashCode(of = "userId")
@AllArgsConstructor
@NoArgsConstructor
//테이스를 위해 추가 추후 삭제
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userNickname;
    private String userProfile;

    private String userLoginType;
    private String personalCode;
    @Column(columnDefinition = "TINYINT(4)")
    private Boolean agreement;
    private LocalDateTime userRegdate;

    //User가 생성될때 cards도 생성되기 때문에 Cascade옵션 킴
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Cards> cardsList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Participants> participantsList = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Reviews> reviewsList = new ArrayList<>();

    //댓글은 User에 완전히 종속된 생명주기를 가짐 따라서 고아 객체 킴
    //고아객체를 키면, repliesList에서 댓글을 remove()하는것 만으로도 DB에 반영됨
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Replies> repliesList = new ArrayList<>();

    //좋아요는 User에 완전히 종속된 생명주기를 가짐 따라서 고아 객체 킴
    //고아객체를 키면, favorsList에서 좋아요를 remove()하는것 만으로도 DB에 반영됨
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Favors> favorsList = new ArrayList<>();

    //추가 login/jw
    //login 관련 정보 저장용
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private UserLoginInfo userLoginInfo;
    /*
    * 연관관계를 편의 메소드 모음
    * */
    //유저에게 카드를 추가하기 위한 메서드
    public void addNewCard(Cards card){
       // card.setUser_id(this.userId);
        this.cardsList.add(card);
    }


}
