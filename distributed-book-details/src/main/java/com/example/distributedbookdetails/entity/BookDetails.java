package com.example.distributedbookdetails.entity;

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
@Document(indexName = "book", shards = 5, replicas = 1, refreshInterval = "1s", indexStoreType = "fs")
public class BookDetails {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String siteName;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String cleanTitle;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String summary;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Date publishTime;

    public BookDetails(String id, String siteName, String author, String title, String cleanTitle, String summary, String content, Date publishTime) {
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
