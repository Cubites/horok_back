package com.metamon.horok.repository;

import com.metamon.horok.domain.Pays;
import com.metamon.horok.dto.SimplePayDTO;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaysRepository extends JpaRepository<Pays, Integer> {
//    @Query("SELECT p, c.cardName, c.cardImg FROM Pays p JOIN p.card c")
//    List<Object[]> findPaysWithCards();
    @Query(value = "SELECT * FROM pays p JOIN cards c ON p.card_number = c.card_number WHERE c.user_id = :userId AND p.expiration = false", nativeQuery = true)
    List<Object[]> findPaysByUserId(@Param("userId") Integer userId);

    @Query("select new com.metamon.horok.dto.SimplePayDTO(p.payId as payId, p.credit as credit, p.payDate as payDate, p.storeName as storeName, p.storeCategory as storeCategory, p.payAddr as payAddr) from Pays p where p.payId = :payId")
    SimplePayDTO findByPayId(@Param("payId") Integer payId);

    @Transactional
    @Modifying
    @Query("UPDATE Pays p SET p.isWritten = true WHERE p.payId = :payId")
    void markAsWritten(@Param("payId") Integer payId);


    @Query("SELECT p FROM Pays p" +
            " WHERE FUNCTION('DATE_FORMAT', ADDDATE(p.payDate, 7), '%Y-%m-%d') <= FUNCTION('DATE_FORMAT', CURRENT_DATE(), '%Y-%m-%d')")
    List<Pays> findExpiredPayList();


    @Query("update Pays p set p.expiration = true where p in :payList")
    @BatchSize(size = 50) //in쿼리 한번에 몇개씩 날릴지 결정
    @Transactional
    @Modifying
    void setExpiredPayList(@Param("payList") List<Pays> expiredPayList);
}
