package com.example.distributedbook.service.external.impl;

import com.example.distributedbook.service.external.ExternalService;
import com.example.distributedbook.service.external.service.BookDetailsService;
import com.example.distributedbook.service.external.service.DistributedAuthenticationService;
import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommon.exception.OperationException;
import com.example.distributedcommondatasource.entity.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ExternalServiceImpl implements ExternalService {

    private final DistributedAuthenticationService distributedAuthenticationService;

    private final BookDetailsService bookDetailsService;

    @Override
    public List<UserDto> findAllUser() {
        ResultMessage resultMessage = distributedAuthenticationService.findAllUser();
        ResultMessage.Meta meta = resultMessage.getMeta();
        if (meta.isSuccess()) {
            return resultMessage.findPageDataList().toJavaList(UserDto.class);
        } else {
            throw new OperationException(meta.getMsg());
        }
    }


    @Override
    public List<String> searchAllRequest() {
        ResultMessage resultMessage = bookDetailsService.searchAllRequest();
        ResultMessage.Meta meta = resultMessage.getMeta();
        if (meta.isSuccess()) {
            return resultMessage.findPageDataList().toJavaList(String.class);
        } else {
            throw new OperationException(meta.getMsg());
        }
    }
}
