package com.example.distributedauthentication.service;

import com.example.distributedauthentication.bo.ResponseUserToken;

public interface LoginService {

    ResponseUserToken login(String username, String password);
}
