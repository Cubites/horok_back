package com.metamon.horok.service;

import com.metamon.horok.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class a {
    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public void dd(){
        //패치조인
        // select u from users u -> n+1문제 -> 프록시조회됨
        // select u from users u join fetch u.team
        //
    }

}
