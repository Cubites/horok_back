package com.metamon.horok.repository;

import com.metamon.horok.domain.UserLoginInfo;
import com.metamon.horok.domain.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserLoginInfoRepository extends JpaRepository<UserLoginInfo,Integer> {
    Optional<UserLoginInfo> findByUserLoginEmail(String email);
    @Query("select u from UserLoginInfo u join fetch u.user")
    Optional<UserLoginInfo> findByEmailWithUserInfo(String email);

    @Query("select u.user from UserLoginInfo u join u.user where u.userLoginEmail =: email")
    Users findByUsersWithEmail(@Param("email") String email);
    @Query("select u.userId, ui.userLoginEmail, ui.userLoginRole from UserLoginInfo ui join ui.user u " +
            "where  ui.userLoginEmail = :email and ui.userLoginProvider = :provider")
    List<Object[]> findUserAndEmail(@Param("email") String email, @Param("provider") String provider);
}
