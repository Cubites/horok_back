package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
@Table(name = "stores")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;
    private String storeName;
    private String storeCategory;
    private String storeAddr;
    private Double latitude;
    private Double longitude;

    @OneToMany
    @JoinColumn(name = "store_id")
    private List<Pays> payList;

}
