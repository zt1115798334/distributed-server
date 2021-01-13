package com.example.distributedbookdetails.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.distributedcommon.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/7 21:23
 * description:
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BookDetailsRest {

    private String id;

    private String siteName;

    private String author;

    private String title;

    private String cleanTitle;

    private String summary;

    private String content;

    @JSONField(format = DateUtils.GREENWICH_DATE_FORMAT_3)
    private Date publishTime;

    public BookDetailsRest(String id, String siteName, String author, String title, String cleanTitle, String summary, String content, Date publishTime) {
        this.id = id;
        this.siteName = siteName;
        this.author = author;
        this.title = title;
        this.cleanTitle = cleanTitle;
        this.summary = summary;
        this.content = content;
        this.publishTime = publishTime;
    }
}
