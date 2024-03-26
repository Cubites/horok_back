package com.metamon.horok.repository;

import com.metamon.horok.domain.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TokenInfoRepository extends JpaRepository<TokenInfo,Integer> {

    TokenInfo findByAccessToken(String accessToken);
    @Query("select ti from TokenInfo ti where ti.email = :email")
    List<TokenInfo> findByEmail(@Param("email") String email);
    @Query("select ti from TokenInfo ti where ti.email = :email and ti.provider = :provider")
    List<TokenInfo> findByEmailAndProvider(@Param("email") String email,@Param("provider") String provider);
}
