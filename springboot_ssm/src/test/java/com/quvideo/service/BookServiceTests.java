package com.quvideo.service;

import com.quvideo.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookServiceTests {
    @Autowired
    private BookService bookService;
    @Test
    void testGetAll() {
        List<Book> bookList = bookService.getAll();
        System.out.println(bookList);
    }

    @Test
    void testGetById() {
        Book book = bookService.getById(2);
        System.out.println(book);
    }

}
