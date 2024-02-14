package com.metamon.horok.repository;

import com.metamon.horok.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface UsersRepository extends JpaRepository<Users,Integer> {

    Users findByUserNickname(String nickname);
    @Query("select u from Users u join fetch u.cardsList where u.userId =: id")
    Users findByUserWithcards(@Param("id") Integer usersId);
}
