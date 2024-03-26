package com.metamon.horok.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class PaysDTO {
    private Integer payId;

    private Integer credit;
    private Timestamp payDate;

    private Byte isWritten;
    private Byte expiration;

    private Integer storeId;
    private String storeName;
    private String storeCategory;
    private String payAddr;

    private String cardName;
    private String cardNumber;
    private String cardImg;
    private String cardLogo;
    private Integer userId;

}
