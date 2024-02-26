package com.metamon.horok.config.secs.oauth.horokjwt;

import com.metamon.horok.domain.TokenInfo;
import com.metamon.horok.domain.TokenInfoRedis;
import com.metamon.horok.repository.TokenInfoRedisRepository;
import com.metamon.horok.repository.TokenInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final TokenInfoRedisRepository redisRepo;
    private final TokenInfoRepository tokenInfoRepository;

    //모든 토큰 정보 삭제
//    public void deleteToken(String accessToken) {
//
//        TokenInfo findToken = tokenInfoRepository.findByAccessToken(accessToken);
//        if(findToken != null) {
//            tokenInfoRepository.delete(findToken);
//        }
//    }
    //Redis에 Token정보 삭제
    public void deleteTokenRedis(String accessToken){
        TokenInfoRedis findToken = redisRepo.findByAccessToken(accessToken).get();
        if(findToken != null){
            redisRepo.delete(findToken);
        }
    }

    // 새로운 access토큰 발급
//    public TokenInfo findRefreshToken(String accessToken) {
//        return tokenInfoRepository.findByAccessToken(accessToken);
//    }

    public TokenInfoRedis findRefreshTokenInRedis(String accessToken) {return redisRepo.findByAccessToken(accessToken).orElse(null);}

//    @Transactional
//    public void updateAccessToken(String acToken, String newAccessToken) {
//        TokenInfo findToken = tokenInfoRepository.findByAccessToken(acToken);
//        findToken.saveTokenValue(newAccessToken);
//    }

    public void updateAccessTokenInRedis(String prevToken, String newAccessToken){
        TokenInfoRedis tokenInfoRedis = redisRepo.findByAccessToken(prevToken).get();
        tokenInfoRedis.updateAccessToken(newAccessToken);
        redisRepo.save(tokenInfoRedis);
    }

//    @Transactional
//    public void saveNewLoginToken(TokenInfo newToken){
//        List<TokenInfo> byEmabyEmailAndProviderl = tokenInfoRepository.findByEmailAndProvider(newToken.getEmail(),newToken.getProvider());
//        if(byEmabyEmailAndProviderl != null){
//            for (TokenInfo tokenInfo : byEmabyEmailAndProviderl) {
//                tokenInfoRepository.delete(tokenInfo);
//            }
//        }
//        tokenInfoRepository.save(newToken);
//    }

    public void saveNewLoginTokenInRedis(TokenInfoRedis newTokenInfo){
        List<TokenInfoRedis> tokenInfoRedisList = redisRepo.findByEmailAndProvider(newTokenInfo.getEmail(), newTokenInfo.getProvider()).get();

        if(tokenInfoRedisList != null && tokenInfoRedisList.size() != 0){
            for (TokenInfoRedis tokenInfoRedis : tokenInfoRedisList) {
                redisRepo.delete(tokenInfoRedis);
            }
        }
        redisRepo.save(newTokenInfo);
    }
}
