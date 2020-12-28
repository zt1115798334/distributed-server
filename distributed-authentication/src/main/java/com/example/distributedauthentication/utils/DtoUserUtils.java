package com.example.distributedauthentication.utils;

import com.example.distributedauthentication.entity.User;
import com.example.distributedcommondatasource.entity.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2020/12/23 15:40
 * description:
 */
public class DtoUserUtils {

    public static List<UserDto> changeUser(List<User> userList) {
        return userList.stream().map(DtoUserUtils::changeUser).collect(Collectors.toList());
    }

    public static UserDto changeUser(User user) {
        return new UserDto(user.getId(), user.getAccount(), user.getUserName(), user.getPhone(), user.getEmail(), user.getCreatedTime());
    }
}
