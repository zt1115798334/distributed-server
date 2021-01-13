package com.example.distributedbook.controller;

import com.example.distributedbook.service.BookService;
import com.example.distributedbook.service.external.BookDetailsService;
import com.example.distributedbook.service.external.UserService;
import com.example.distributedcommon.base.BaseController;
import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommon.vo.VoBook;
import com.example.distributedcommondatasource.entity.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    private final UserService userService;

    private final BookDetailsService bookDetailsService;

    @PostMapping("saveBook")
//    public ResultMessage saveBook(@RequestBody VoBook voBook) {
    public ResultMessage saveBook(@RequestParam String bookName) {
        Random random = new Random();
        List<UserDto> userDtoList = userService.findAllUser();
        List<String> bookIds = bookDetailsService.searchAllRequest();
        for (UserDto userDto : userDtoList) {
            new Thread(() -> {
                for (String bookId : bookIds) {
                    VoBook voBook = new VoBook();
                    voBook.setBookName(bookName + random.nextInt());
                    bookService.saveBooK(userDto.getId(), bookId, voBook);
                }
            }).start();
        }
        return success();
    }
}
