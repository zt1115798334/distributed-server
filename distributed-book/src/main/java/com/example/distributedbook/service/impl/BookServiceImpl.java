package com.example.distributedbook.service.impl;

import com.example.distributedbook.entity.Book;
import com.example.distributedbook.repo.BookRepository;
import com.example.distributedbook.service.BookService;
import com.example.distributedbook.utils.DtoUtils;
import com.example.distributedcommon.vo.VoBook;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2020/12/21 17:17
 * description:
 */
@AllArgsConstructor
@Service
@Transactional(rollbackOn = RuntimeException.class)
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        Long id = book.getId();
        long generateKey = generateKey();
        return Optional.ofNullable(id).filter(i -> i != 0L)
                .map(i -> {
                    Optional<Book> bookOfDb = bookRepository.findById(i);
                    Book bookOfNeed = bookOfDb.map(b -> {
                        b.setBookName(book.getBookName());
                        return b;
                    }).orElseGet(() -> {
                        book.setId(generateKey);
                        book.setCreatedTime(currentDateTime);
                        book.setDeleteState(UN_DELETED);
                        return book;
                    });
                    return bookRepository.save(bookOfNeed);
                })
                .orElseGet(() -> {
                    book.setId(generateKey);
                    book.setCreatedTime(currentDateTime);
                    book.setDeleteState(UN_DELETED);
                    return bookRepository.save(book);
                });
    }

    @Override
    public Book saveBooK(Long userId, String bookId, VoBook voBook) {
        Book book = DtoUtils.changeBook(voBook);
        book.setUserId(userId);
        return this.save(book);
    }

    @Override
    public List<Book> findBookAll() {
        return (List<Book>) bookRepository.findAll();
    }
}
