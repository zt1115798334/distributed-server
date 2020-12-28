package com.example.distributedbook.service.external;

import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommondatasource.entity.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Component
@FeignClient(value = "service-distributed-authentication")
public interface DistributedAuthenticationService {

    @GetMapping(value = "api/user/findAllUser")
    ResultMessage findAllUser();
}
