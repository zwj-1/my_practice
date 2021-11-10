package com.zwj.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = MyOauthExceptionSerializer.class)
public class MyOauthException extends OAuth2Exception {

    public MyOauthException(String msg, Throwable t) {
        super(msg, t);
    }

    public MyOauthException(String msg) {
        super(msg);
    }
}
