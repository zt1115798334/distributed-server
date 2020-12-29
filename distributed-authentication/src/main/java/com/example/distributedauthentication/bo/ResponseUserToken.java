package com.example.distributedauthentication.bo;

import com.example.distributedauthentication.security.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseUserToken {
    private String token;
    private SecurityUser userDetail;
}
