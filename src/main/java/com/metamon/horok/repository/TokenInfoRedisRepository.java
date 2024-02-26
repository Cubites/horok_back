package com.metamon.horok.repository;

import com.metamon.horok.domain.TokenInfoRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenInfoRedisRepository extends CrudRepository<TokenInfoRedis,String> {

    Optional<TokenInfoRedis> findByAccessToken(String accessToken);
    Optional<List<TokenInfoRedis>> findByEmailAndProvider(String email, String provider);
}
