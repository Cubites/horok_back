package com.metamon.horok.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payDate;
    @Column(columnDefinition = "TINYINT(4)")
    private Boolean isWritten;
    @Column(columnDefinition = "TINYINT(4)")
    private Boolean expiration;
    private String storeName;
    private String storeCategory;
    private String payAddr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="card_number")
    private Cards card;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="store_id")
    private Stores store;

}
