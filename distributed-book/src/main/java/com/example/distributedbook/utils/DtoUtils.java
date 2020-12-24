package com.example.distributedbook.utils;

import com.example.distributedbook.entity.Book;
import com.example.distributedcommon.vo.VoBook;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2020/12/23 15:40
 * description:
 */
public class DtoUtils {
    public static Book changeBook(VoBook voBook) {
        Book book = new Book();
        book.setBookName(voBook.getBookName());
        return book;
    }
}
