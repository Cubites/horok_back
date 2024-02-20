package com.metamon.horok.repository;

import com.metamon.horok.domain.Stores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoresRepository extends JpaRepository<Stores, Integer> {
    Stores findByStoreName(String storeName);
}
