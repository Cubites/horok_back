package com.metamon.horok.repository;

import com.metamon.horok.domain.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardsRepository extends JpaRepository<Cards,String> {
//    @Query("select distinct(c) from Cards c left join fetch c.paysList where c.cardNumber = :cardNumber")
//    List<Cards> findByCardNumberWithPays(@Param("cardNumber") String cardNumber);
//    @Query("SELECT DISTINCT c FROM Cards c LEFT JOIN FETCH c.payList WHERE c.cardNumber = :cardNumber")
//    List<Cards> findByCardNumberWithPays(@Param("cardNumber") String cardNumber);
//    @Query("SELECT c, p FROM Pays p LEFT JOIN FETCH p.card c WHERE c.cardNumber = :cardNumber")
//    List<Object[]> findByCardNumberWithPays(@Param("cardNumber") String cardNumber);


}
