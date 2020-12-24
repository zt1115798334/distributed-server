package com.example.distributedbook.controller;

import com.example.distributedbook.service.BookService;
import com.example.distributedcommon.base.BaseController;
import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommon.vo.VoBook;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2020/12/21 17:41
 * description:
 */
@AllArgsConstructor
@RequestMapping("api/book")
@RestController
public class BookController extends BaseController {

    private final BookService bookService;

    @PostMapping("saveBook")
//    public ResultMessage saveBook(@RequestBody VoBook voBook) {
    public ResultMessage saveBook(@RequestParam String bookName) {
        Random random = new Random();
        for (long i = 1L; i <= 100L; i++) {
            VoBook voBook = new VoBook();
            voBook.setBookName(bookName + random.nextInt());
            Long userId = i;
            bookService.saveBooK(userId, voBook);
        }
        return success();
    }
}
