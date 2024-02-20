package com.metamon.horok.repository;

import com.metamon.horok.domain.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews,Integer> {
    Integer countByUser_UserId(Integer userId);
}
