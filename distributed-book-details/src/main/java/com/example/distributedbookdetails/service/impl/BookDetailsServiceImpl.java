package com.example.distributedbookdetails.service.impl;

import com.example.distributedbookdetails.entity.BookDetails;
import com.example.distributedbookdetails.repo.BookDetailsRepository;
import com.example.distributedbookdetails.service.BookDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

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
    public Page<BookDetails> findByContent(int pageNumber, int pageSize, String content) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return bookDetailsRepository.findByContent(content, pageRequest);
    }

    @Override
    public Page<BookDetails> findByFirstCode(int pageNumber, int pageSize, String firstCode) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return bookDetailsRepository.findByFirstCode(firstCode, pageRequest);
    }

    @Override
    public Page<BookDetails> findBySecondCode(int pageNumber, int pageSize, String secondCode) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return bookDetailsRepository.findBySecondCode(secondCode, pageRequest);
    }

    @Override
    public Page<BookDetails> query(String key) {
        return null;
    }
}
