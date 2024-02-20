package com.metamon.horok.config.secs.oauth.horokjwt;

import com.metamon.horok.domain.TokenInfo;
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


    private final TokenInfoRepository tokenInfoRepository;

    //모든 토큰 정보 삭제
    public void deleteToken(String accessToken) {

        TokenInfo findToken = tokenInfoRepository.findByAccessToken(accessToken);
        if(findToken != null) {
            tokenInfoRepository.delete(findToken);
        }
    }

    // 새로운 access토큰 발급
    public TokenInfo findRefreshToken(String accessToken) {
        return tokenInfoRepository.findByAccessToken(accessToken);
    }
    @Transactional
    public void updateAccessToken(String acToken, String newAccessToken) {
        TokenInfo findToken = tokenInfoRepository.findByAccessToken(acToken);
        findToken.saveTokenValue(newAccessToken);
    }

    @Transactional
    public void saveNewLoginToken(TokenInfo newToken){
        List<TokenInfo> byEmabyEmailAndProviderl = tokenInfoRepository.findByEmailAndProvider(newToken.getEmail(),newToken.getProvider());
        if(byEmabyEmailAndProviderl != null){
            for (TokenInfo tokenInfo : byEmabyEmailAndProviderl) {
                tokenInfoRepository.delete(tokenInfo);
            }
        }
        tokenInfoRepository.save(newToken);
    }
}
