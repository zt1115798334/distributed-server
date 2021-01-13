package com.example.distributedbook.service.external;

import com.example.distributedcommondatasource.entity.dto.UserDto;

import java.util.List;

public interface ExternalService {

    List<UserDto> findAllUser();

    List<String> searchAllRequest();

}
