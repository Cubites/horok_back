package com.metamon.horok.repository;

import com.metamon.horok.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
    Users findByUserNickname(String nickname);
//    Users findByUserProfile(String userProfile);
}
