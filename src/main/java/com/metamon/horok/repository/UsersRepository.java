package com.metamon.horok.repository;

import com.metamon.horok.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUserId(Integer userId);

    Users findByUserNickname(String nickname);

    @Query("select u from Users u join fetch u.cardsList where u.userId =: id")
    Users findByUserWithcards(@Param("id") Integer usersId);

    // 닉네임 수정
    @Modifying
    @Query("update Users set userNickname = :userNickname where userId = :userId")
    int updateUserNickname(@Param("userNickname") String userNickname, @Param("userId") Integer userId);

    // 프로필 수정
    @Modifying
    @Query("update Users set userProfile = :userProfile where userId = :userId")
    void updateUserProfile(@Param("userProfile") String userProfile, @Param("userId") Integer userId);

    //이번달 카테고리별 사용내역
//    @Query(("SELECT p.storeCategory, SUM(p.credit) " +
//            "FROM Users u " +
//            "JOIN u.cardsList c " +
//            "JOIN c.payList p " +
//            "WHERE u.userId = :userId " +
//            "AND YEAR(p.payDate) = YEAR(CURRENT_DATE()) " +
//            "AND MONTH(p.payDate) = MONTH(CURRENT_DATE()) " +
//            "GROUP BY p.storeCategory"))
//    List<Object[]> findMonthlyCardUsageByCategory(@Param("userId") Integer userId);
    @Query(("SELECT p.storeCategory, SUM(p.credit) " +
            "FROM Users u " +
            "JOIN u.cardsList c " +
            "JOIN c.payList p " +
            "WHERE u.userId = :userId " +
            "AND YEAR(p.payDate) = YEAR(CURRENT_DATE()) " +
            "AND MONTH(p.payDate) = MONTH(CURRENT_DATE()) " +
            "AND c.cardNumber IN (:cardNumber) " +
            "GROUP BY p.storeCategory"))
    List<Object[]> findMonthlyCardUsageByCategory(@Param("userId") Integer userId, @Param("cardNumber") List<String> cardNumber);

    //해당연도 카테고리별 사용내역
//    @Query("SELECT p.storeCategory, SUM(p.credit) " +
//            "FROM Users u " +
//            "JOIN u.cardsList c " +
//            "JOIN c.payList p " +
//            "WHERE u.userId = :userId " +
//            "AND YEAR(p.payDate) = YEAR(CURRENT_DATE()) " +
//            "GROUP BY p.storeCategory")
//    List<Object[]> findYearlyCardUsageByCategory(@Param("userId") Integer userId);

        @Query("SELECT p.storeCategory, SUM(p.credit) " +
            "FROM Users u " +
            "JOIN u.cardsList c " +
            "JOIN c.payList p " +
            "WHERE u.userId = :userId " +
            "AND YEAR(p.payDate) = YEAR(CURRENT_DATE()) " +
            "AND c.cardNumber IN (:cardNumber) " +
            "GROUP BY p.storeCategory")
    List<Object[]> findYearlyCardUsageByCategory(@Param("userId") Integer userId, @Param("cardNumber") List<String> cardNumber);


    // MapTest용
    @Query("SELECT u FROM Users u join fetch u.reviewsList where u.userNickname = :name")
    Users findByUserWithReviews(@Param("name") String userName);

}
