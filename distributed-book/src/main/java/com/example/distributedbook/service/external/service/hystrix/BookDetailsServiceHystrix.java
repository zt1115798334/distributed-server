package com.example.distributedbook.service.external.service.hystrix;

import com.example.distributedbook.service.external.service.BookDetailsService;
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
public class BookDetailsServiceHystrix extends BaseResultMessage implements BookDetailsService {
    @Override
    public ResultMessage searchAllRequest() {
        log.error("无法链接啊");
        return failure("无法链接啊");
    }
}
