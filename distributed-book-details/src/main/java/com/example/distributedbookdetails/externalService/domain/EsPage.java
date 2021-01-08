package com.example.distributedbookdetails.externalService.domain;

import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/8/30 15:58
 * description: es分页类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsPage<T> {

    private int pageNumber;

    private int pageSize;

    private List<T> list = Collections.emptyList();

    private JSONArray jsonArray = new JSONArray();

    private Long totalElements = 0L;

    public EsPage(List<T> list, Long totalElements) {
        this.list = list;
        this.totalElements = totalElements;
    }

    public EsPage(JSONArray jsonArray, Long totalElements) {
        this.jsonArray = jsonArray;
        this.totalElements = totalElements;
    }

    public EsPage(int pageNumber, int pageSize, List<T> list, Long totalElements) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.list = list;
        this.totalElements = totalElements;
    }
}
