package com.example.distributedbook.service.external.service.hystrix;

import com.example.distributedbook.service.external.service.DistributedAuthenticationService;
import com.example.distributedcommon.base.BaseResultMessage;
import com.example.distributedcommon.base.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/13 15:30
 * description:
 */
@Slf4j
@Component
public class DistributedAuthenticationServiceHystrix extends BaseResultMessage implements DistributedAuthenticationService {

    @Override
    public ResultMessage findAllUser() {
        log.error("无法链接啊");
        return failure("无法链接啊");
    }
}
