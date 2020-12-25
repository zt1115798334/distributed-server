package com.example.distributedauthentication.service;


import com.example.distributedauthentication.entity.User;
import com.example.distributedcommondatasource.service.BaseService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2020/12/21 17:17
 * description:
 */
public interface UserService extends BaseService<User, Long, Long> {

    void createdUser(String account, String password, String userName, String phone, String email);
}
