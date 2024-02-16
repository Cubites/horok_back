package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@Table(name = "stores")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Stores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;
    private String storeName;
    private String storeCategory;
    private String storeAddr;
    private Double latitude;
    private Double longitude;
}
