package com.metamon.horok.config.secs.oauth.horokjwt;

public class AccessTokenExpiredException extends RuntimeException{
   public AccessTokenExpiredException(String msg){
        super(msg);
    }
}
