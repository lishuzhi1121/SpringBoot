package com.quvideo.controller;

import com.quvideo.domain.Book;
import com.quvideo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @PostMapping
    public Result save(@RequestBody Book book) {
        boolean success = bookService.save(book);
        return new Result((success ? Code.SAVE_OK : Code.SAVE_ERR), success);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean success = bookService.delete(id);
        return new Result((success ? Code.DELETE_OK : Code.DELETE_ERR), success);
    }

    @PutMapping
    public Result update(@RequestBody Book book) {
        boolean success = bookService.update(book);
        return new Result((success ? Code.UPDATE_OK : Code.UPDATE_ERR), success);
    }

    @GetMapping
    public Result getAll() {
        List<Book> books = bookService.getAll();
        Integer code = books == null ? Code.GET_ERR : Code.GET_OK;
        String msg = books == null ? "数据查询失败, 请检查请求参数后重试！": null;
        return new Result(code, msg, books);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        Integer code = book == null ? Code.GET_ERR : Code.GET_OK;
        String msg = book == null ? "数据查询失败, 请检查请求参数后重试！": null;
        return new Result(code, msg, book);
    }

    @GetMapping("/name/{name}")
    public Result getByName(@PathVariable String name) {
        Book book = bookService.getByName(name);
        Integer code = book == null ? Code.GET_ERR : Code.GET_OK;
        String msg = book == null ? "数据查询失败, 请检查请求参数后重试！": null;
        return new Result(code, msg, book);
    }

    @GetMapping("/keyword/{keyword}")
    public Result getByNameKeyword(@PathVariable String keyword) {
        List<Book> books = bookService.getByNameKeyword(keyword);
        Integer code = books == null ? Code.GET_ERR : Code.GET_OK;
        String msg = books == null ? "数据查询失败, 请检查请求参数后重试！": null;
        return new Result(code, msg, books);
    }

}
