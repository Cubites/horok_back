package com.metamon.horok.repository;

import com.metamon.horok.domain.Reviews;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

    // MapTest 용
    @Query("SELECT r FROM Reviews r JOIN r.store s JOIN r.user u WHERE s.storeName = :name AND u.userNickname = :nname")
    public Reviews findByReviewWithStore(@Param("name") String storeName, @Param("nname") String nickname);
}
