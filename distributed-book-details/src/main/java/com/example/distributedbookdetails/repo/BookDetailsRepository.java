package com.example.distributedbookdetails.repo;

import com.example.distributedbookdetails.entity.BookDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/1/7 21:27
 * description:
 */
public interface BookDetailsRepository extends ElasticsearchRepository<BookDetails, Long> {

//    //默认的注释
//    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"content\" : \"?\"}}}}")
//    Page<BookDetails> findByContent(String content, Pageable pageable);
//
//    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"firstCode.keyword\" : \"?\"}}}}")
//    Page<BookDetails> findByFirstCode(String firstCode, Pageable pageable);
//
//    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"secondCode.keyword\" : \"?\"}}}}")
//    Page<BookDetails> findBySecondCode(String secondCode, Pageable pageable);
}
