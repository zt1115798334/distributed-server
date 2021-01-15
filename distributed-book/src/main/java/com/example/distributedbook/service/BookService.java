package com.example.distributedbook.service;


import com.example.distributedbook.entity.Book;
import com.example.distributedcommon.vo.VoBook;
import com.example.distributedcommondatasource.service.BaseService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2020/12/21 17:17
 * description:
 */
public interface BookService extends BaseService<Book, Long, Long> {

    Book saveBooK(Long userId,String bookId, VoBook voBook);

    List<Book> findBookAll();
}
