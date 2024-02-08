package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "pays")
@EqualsAndHashCode(of = "payId")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer payId;

    private Integer credit;
    private LocalDateTime payDate;
    @Column(columnDefinition = "TINYINT(4)")
    private Boolean isWritten;
    @Column(columnDefinition = "TINYINT(4)")
    private Boolean expiration;
    private String storeName;
    private String storeCategory;
    private String payAddr;

}
