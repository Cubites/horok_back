package com.metamon.horok.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
//빌더 필수 값 지정을 위해 사용
@Builder(builderMethodName = "innerBuilder")
@Entity
@Table(name = "cards")
@EqualsAndHashCode(of="cardNumber")
@AllArgsConstructor

public class Cards{

    @Id
    //직접할당해야함
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String cardNumber;

    //카드를 등록할 때 user_id값이 필수임, 단방향 매핑이라 내부적으로 특정 값이 필요

    private Integer user_id;
    private String cardName;
    private String cardImg;

    @OneToMany
    @JoinColumn(name = "card_number")
    private List<Pays> payList = new ArrayList<>();


    // builder 메소드 재정의 필수값 지정 -> 카드번호는 AI사용x 카드 생성시 무조건 입력받도록 재정의
    public static CardsBuilder builder(String cardNumber){
        return innerBuilder().cardNumber(cardNumber);
    }
    //jpa
    protected Cards(){

    }

    public void setUser_id(Integer userId){
        this.user_id = userId;

    }


}
