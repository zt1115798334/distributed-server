package com.example.distributedbook.repo;

import com.example.distributedbook.entity.Book;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2020/12/21 17:17
 * description:
 */
public interface BookRepository extends CrudRepository<Book, Long> {
}
