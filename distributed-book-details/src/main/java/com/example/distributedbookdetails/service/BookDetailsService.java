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

    Page<BookDetails> searchBook(int pageNumber, int pageSize, String keyword);

    Page<BookDetails> query(String key);
}
