package com.metamon.horok.dto;

import com.metamon.horok.domain.Cards;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class CardDTO {
    private Integer userId;

    private String cardNumber;
    private String cardName;
    private String cardImg;
    private String cardLogo;


    //마이페이지 카드 리스트 출력하기 위해서
    public static CardDTO toDto(Cards entity) {
        return CardDTO.builder()
                .cardNumber(entity.getCardNumber())
                .cardName(entity.getCardName())
                .cardImg(entity.getCardImg())
                .build();
    }
}
