package com.example.distributedbookdetails.service.impl;

import com.example.distributedbookdetails.entity.BookDetails;
import com.example.distributedbookdetails.repo.BookDetailsRepository;
import com.example.distributedbookdetails.service.BookDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/7 21:28
 * description:
 */
@Slf4j
@AllArgsConstructor
@Service
public class BookDetailsServiceImpl implements BookDetailsService {

    private final BookDetailsRepository bookDetailsRepository;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void createIndex() {
        boolean exists = elasticsearchRestTemplate.indexOps(BookDetails.class).exists();
        if (!exists) {
            log.info("索引不存在进行创建");
            elasticsearchRestTemplate.indexOps(BookDetails.class).create();
        }
    }

    @Override
    public void deleteIndex(String index) {
        elasticsearchRestTemplate.indexOps(String.class).delete();
    }

    @Override
    public void save(BookDetails bookDetails) {
        bookDetailsRepository.save(bookDetails);
    }

    @Override
    public void saveAll(List<BookDetails> list) {
        bookDetailsRepository.saveAll(list);
    }

    @Override
    public Iterator<BookDetails> findAll() {
        return bookDetailsRepository.findAll().iterator();
    }

    @Override
    public Page<BookDetails> searchBook(int pageNumber, int pageSize, String keyword) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.boolQuery()
                .should(QueryBuilders.wildcardQuery("cleanTitle.keyword", "*" + keyword + "*"))
        );

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withSearchType(SearchType.DEFAULT)
                .withPageable(pageRequest)//加折叠和分页
                .build();


        SearchHits<BookDetails> searchHits = elasticsearchRestTemplate.search(build, BookDetails.class);
        List<BookDetails> bookDetailsList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(bookDetailsList, pageRequest, searchHits.getTotalHits());
    }

    @Override
    public Page<BookDetails> query(String key) {
        return null;
    }
}
