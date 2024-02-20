package com.metamon.horok.repository;

import com.metamon.horok.domain.Cards;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface CardsRepository extends JpaRepository<Cards,String> {

//    //    List<Cards> findByUserId(Integer userId);
//    @Query(value = "SELECT card_number,card_name ,card_img FROM cards c WHERE c.user_id =: userId ")
//    public List<Cards> getCardsList(@Param("user_Id") Integer userId);
//
//    Optional<Cards> findById(Integer userId);
}
