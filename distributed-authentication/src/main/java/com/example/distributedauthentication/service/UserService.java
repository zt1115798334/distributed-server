package com.example.distributedauthentication.service;


import com.example.distributedauthentication.entity.User;
import com.example.distributedcommondatasource.service.BaseService;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2020/12/21 17:17
 * description:
 */
public interface UserService extends BaseService<User, Long, Long> {

    void createdUser(String account, String password, String userName, String phone, String email);

    List<User> findAllUser();

    Optional<User> findOptUserByAccount(String account);

    User findUserByAccount(String account);
}
