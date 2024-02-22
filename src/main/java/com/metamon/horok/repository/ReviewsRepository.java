package com.metamon.horok.repository;

import com.metamon.horok.domain.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

    Integer countByUser_UserId(Integer userId);

     //MapTest 용
    @Query("SELECT r FROM Reviews r JOIN r.store s JOIN r.user u WHERE s.storeName = :name AND u.userNickname = :nname")
    public Reviews findByReviewWithStore(@Param("name") String storeName, @Param("nname") String nickname);

    @Query("select r from Reviews r join r.folderReviewsList fr on fr.folderId = :folderId")
    public List<Reviews> findReviewsWithFolder(@Param("folderId")Integer folderId);

}
