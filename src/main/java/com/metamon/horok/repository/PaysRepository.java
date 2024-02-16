package com.metamon.horok.repository;

import com.metamon.horok.domain.Pays;
import com.metamon.horok.dto.SimplePayDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaysRepository extends JpaRepository<Pays, Integer> {
//    @Query("SELECT p, c.cardName, c.cardImg FROM Pays p JOIN p.card c")
//    List<Object[]> findPaysWithCards();
    @Query(value = "SELECT * FROM pays p JOIN cards c ON p.card_number = c.card_number WHERE c.user_id = :userId AND p.expiration = false", nativeQuery = true)
    List<Object[]> findPaysByUserId(@Param("userId") Integer userId);

    @Query("select new com.metamon.horok.dto.SimplePayDTO(p.payId as payId, p.credit as credit, p.payDate as payDate, p.storeName as storeName, p.storeCategory as storeCategory, p.payAddr as payAddr) from Pays p where payId = :payId")
    SimplePayDTO findByPayId(@Param("payId") Integer payId);
}
