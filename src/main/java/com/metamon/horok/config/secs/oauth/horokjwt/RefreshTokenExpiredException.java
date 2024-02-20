package com.metamon.horok.config.secs.oauth.horokjwt;

public class RefreshTokenExpiredException extends RuntimeException{
   public RefreshTokenExpiredException(String msg){
        super(msg);
    }
}
