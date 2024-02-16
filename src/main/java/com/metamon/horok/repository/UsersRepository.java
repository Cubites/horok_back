package com.metamon.horok.repository;

import com.metamon.horok.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
    Users findByUserNickname(String nickname);

    @Query("select u from Users u join fetch u.cardsList where u.userId =: id")
    Users findByUserWithcards(@Param("id") Integer usersId);

    // MapTest용
    @Query("SELECT u FROM Users u join fetch u.reviewsList where u.userNickname = :name")
    Users findByUserWithReviews(@Param("name") String userName);
}
