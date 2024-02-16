package com.metamon.horok.repository;

import com.metamon.horok.domain.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenInfoRepository extends JpaRepository<TokenInfo,Integer> {

    TokenInfo findByAccessToken(String accessToken);
}
