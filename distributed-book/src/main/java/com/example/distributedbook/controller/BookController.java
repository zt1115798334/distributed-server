package com.example.distributedbook.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.distributedbook.service.BookService;
import com.example.distributedbook.service.external.ExternalService;
import com.example.distributedbook.service.external.service.BookDetailsService;
import com.example.distributedcommon.base.BaseResultMessage;
import com.example.distributedcommon.base.ResultMessage;
import com.example.distributedcommon.vo.VoBook;
import com.example.distributedcommondatasource.entity.dto.UserDto;
import com.google.common.collect.Lists;
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
public class BookController extends BaseResultMessage {

    private final BookService bookService;

    private final ExternalService externalService;

    private final BookDetailsService bookDetailsService;

    @PostMapping("saveBook")
//    public ResultMessage saveBook(@RequestBody VoBook voBook) {
    public ResultMessage saveBook(@RequestParam String bookName) {
        Random random = new Random();
        List<UserDto> userDtoList = externalService.findAllUser();
        System.out.println("userDtoList = " + userDtoList);
//        List<String> bookIds = externalService.searchAllRequest();
//        for (UserDto userDto : userDtoList) {
//            new Thread(() -> {
//                for (String bookId : bookIds) {
//                    VoBook voBook = new VoBook();
//                    voBook.setBookName(bookName + random.nextInt());
//                    bookService.saveBooK(userDto.getId(), bookId, voBook);
//                }
//            }).start();
//        }
        return success();
    }

    @PostMapping("test1")
//    public ResultMessage saveBook(@RequestBody VoBook voBook) {
    public ResultMessage test1() {

        JSONObject pp = new JSONObject();
        pp.put("ddd", "dd");
        return success(pp);
    }

    @PostMapping("test2")
//    public ResultMessage saveBook(@RequestBody VoBook voBook) {
    public ResultMessage test2() {
        List<JSONObject> list = Lists.newArrayList();
        JSONObject pp = new JSONObject();
        pp.put("ddd", "dd");
        list.add(pp);
        return success(1, 10, 100, list);
    }
    @PostMapping("test3")
//    public ResultMessage saveBook(@RequestBody VoBook voBook) {
    public ResultMessage test3() {
        List<JSONObject> list = Lists.newArrayList();
        JSONObject pp = new JSONObject();
        pp.put("ddd", "dd");
        list.add(pp);
        return success(list);
    }

}
