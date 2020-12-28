package com.example.distributedbook.service.external.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.distributedbook.service.external.DistributedAuthenticationService;
import com.example.distributedbook.service.external.UserService;
import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommondatasource.entity.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final DistributedAuthenticationService distributedAuthenticationService;

    @Override
    public List<UserDto> findAllUser() {
        ResultMessage resultMessage = distributedAuthenticationService.findAllUser();
        JSONObject meta = resultMessage.getMeta();
        if (meta.getBoolean("success")) {
            return resultMessage.getData().getJSONArray("list").toJavaList(UserDto.class);
        }
        return Collections.emptyList();
    }
}
