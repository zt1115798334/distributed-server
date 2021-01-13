package com.example.distributedbook.service.external.service;

import com.example.distributedbook.service.external.service.hystrix.DistributedAuthenticationServiceHystrix;
import com.example.distributedcommon.base.ResultMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "service-distributed-authentication", fallback = DistributedAuthenticationServiceHystrix.class)
public interface DistributedAuthenticationService {

    @GetMapping(value = "api/user/findAllUser")
    ResultMessage findAllUser();
}
