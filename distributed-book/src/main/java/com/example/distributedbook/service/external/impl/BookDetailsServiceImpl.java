package com.example.distributedbook.service.external.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.distributedbook.service.external.BookDetailsService;
import com.example.distributedbook.service.external.inter.BookDetailsServiceInter;
import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommondatasource.entity.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/13 11:34
 * description:
 */
@AllArgsConstructor
@Service
public class BookDetailsServiceImpl implements BookDetailsService {

    private final BookDetailsServiceInter bookDetailsServiceInter;

    @Override
    public List<String> searchAllRequest() {
        ResultMessage resultMessage = bookDetailsServiceInter.searchAllRequest();
        JSONObject meta = resultMessage.getMeta();
        if (meta.getBoolean("success")) {
            return resultMessage.getData().getJSONArray("list").toJavaList(String.class);
        }
        return Collections.emptyList();
    }
}
