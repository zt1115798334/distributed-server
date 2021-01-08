package com.example.distributedbookdetails.service;

import com.example.distributedbookdetails.entity.BookDetails;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/7 21:27
 * description:
 */
public interface BookDetailsService {
    void createIndex();

    void deleteIndex(String index);

    void save(BookDetails bookDetails);

    void saveAll(List<BookDetails> list);

    Iterator<BookDetails> findAll();

    Page<BookDetails> findByContent(int pageNumber, int pageSize, String content);

    Page<BookDetails> findByFirstCode(int pageNumber, int pageSize, String firstCode);

    Page<BookDetails> findBySecondCode(int pageNumber, int pageSize, String secondCode);

    Page<BookDetails> query(String key);
}
