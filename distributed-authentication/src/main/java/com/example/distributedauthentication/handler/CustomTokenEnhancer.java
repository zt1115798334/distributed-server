package com.example.distributedauthentication.handler;

import com.example.distributedauthentication.security.SecurityUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/4 16:13
 * description:
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInformation = new HashMap<>();
        SecurityUser user = (SecurityUser) authentication.getUserAuthentication().getPrincipal();
        //把用户的主键userId放进去
        additionalInformation.put("userId", user.getId());
        additionalInformation.put("username", user.getUsername());
        additionalInformation.put("authorities", user.getAuthorities());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);

        additionalInformation.put("organization", authentication.getName() + RandomStringUtils.randomAlphabetic(4));

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        return accessToken;
    }
}
