package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "userLoginInfo")
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userLoginInfoId;

    //login/jw 에서 추가
    //user role
    private String userLoginRole;
    //login email
    private String userLoginEmail;
    private String userLoginProvider;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    public void setUser(Users user) {
        this.user = user;
    }

}