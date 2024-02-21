package com.metamon.horok.repository;

import com.metamon.horok.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    // 사용자가 가지고 있는 카드별 월별 카데고리별 사용내역의 합계 ( 전체 선택된 경우라고 생각하면 될듯 ? )
    // @Query("SELECT c.cardName, p.storeCategory, SUM(p.credit) " +
    // "FROM Users u " +
    // "JOIN u.cardsList c " +
    // "JOIN c.payList p " +
    // "WHERE u.userId = :userId " +
    // "AND YEAR(p.payDate) = YEAR(CURRENT_DATE()) " +
    // "AND MONTH(p.payDate) = MONTH(CURRENT_DATE()) " +
    // "GROUP BY c.cardName, p.storeCategory")
    // List<Object[]> findMonthlyCardUsageByCategory(@Param("userId") Integer
    // userId);

    // 카테고리별로 나누기만하고 카테고리별 사용자의 월별 사용금액의 총합을 나타내는 쿼리문 통계


//    @Query("SELECT YEAR(p.payDate), MONTH(p.payDate), p.storeCategory, SUM(p.credit)" +
//            "FROM Users u " +
//            "JOIN u.cardsList c " +
//            "JOIN c.payList p " +
//            "WHERE u.userId = :userId " +
//            "GROUP BY YEAR(p.payDate), MONTH(p.payDate), p.storeCategory")
//    List<Object[]> findMonthlyCardUsageByCategory(@Param("userId") Integer userId);
@Query("SELECT p.storeCategory, SUM(p.credit) " +
        "FROM Users u " +
        "JOIN u.cardsList c " +
        "JOIN c.payList p " +
        "WHERE u.userId = :userId " +
        "AND YEAR(p.payDate) = YEAR(CURRENT_DATE()) " +
        "AND MONTH(p.payDate) = MONTH(CURRENT_DATE()) " +
        "GROUP BY p.storeCategory")
    List<Object[]> findMonthlyCardUsageByCategory(@Param("userId") Integer userId);

    // MapTest용
    @Query("SELECT u FROM Users u join fetch u.reviewsList where u.userNickname = :name")
    Users findByUserWithReviews(@Param("name") String userName);

}
