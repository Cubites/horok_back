package com.metamon.horok.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
//빌더 필수 값 지정을 위해 사용
@Builder(builderMethodName = "innerBuilder")
@Entity
@Table(name = "cards")
@EqualsAndHashCode(of="cardNumber")
@AllArgsConstructor
@NoArgsConstructor
public class Cards{

    @Id
    // 직접 할당하는 값이므로 @GeneratedValue는 사용하지 않음
    private String cardNumber;

    private String cardName;
    private String cardImg;

    @OneToMany(fetch = FetchType.EAGER) // TODO
    @JoinColumn(name = "card_number")
    private List<Pays> payList;

    // builder 메소드 재정의 필수값 지정 -> 카드번호는 AI사용x 카드 생성시 무조건 입력받도록 재정의
    public static CardsBuilder builder(String cardNumber){
        return innerBuilder().cardNumber(cardNumber);
    }
    //jpa


}
