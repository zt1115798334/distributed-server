package com.example.distributedbook.service.external;

import com.example.distributedcommondatasource.entity.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAllUser();
}
