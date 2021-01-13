package com.example.distributedbook.service.external.inter;

import com.example.distributedcommon.base.ResultMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "service-distributed-book-details")
public interface BookDetailsServiceInter {

    @PostMapping(value = "/api/bookDetailsRest/searchAllRequest")
    ResultMessage searchAllRequest();
}
