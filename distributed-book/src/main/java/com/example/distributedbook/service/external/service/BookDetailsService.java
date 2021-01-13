package com.example.distributedbook.service.external.service;

import com.example.distributedbook.service.external.service.hystrix.BookDetailsServiceHystrix;
import com.example.distributedcommon.base.ResultMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "service-distributed-book-details", fallback = BookDetailsServiceHystrix.class)
public interface BookDetailsService {

    @PostMapping(value = "/api/bookDetailsRest/searchAllRequest")
    ResultMessage searchAllRequest();
}
