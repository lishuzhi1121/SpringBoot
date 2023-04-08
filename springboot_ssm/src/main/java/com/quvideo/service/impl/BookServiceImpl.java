package com.quvideo.service.impl;

import com.quvideo.dao.BookDao;
import com.quvideo.domain.Book;
import com.quvideo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public boolean save(Book book) {
        int count = bookDao.save(book);
        return count > 0;
    }

    @Override
    public boolean delete(Integer id) {
        int count = bookDao.delete(id);
        return count > 0;
    }

    @Override
    public boolean update(Book book) {
        int count = bookDao.update(book);
        return count > 0;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book getById(Integer id) {
//        if (id == 1) {
//            throw new BusinessException(Code.BUSINESS_ERR, "这不是一个正常的id, 根本无法处理！！！");
//        }
//        try {
//            int i = 1/0;
//        } catch (Exception ex) {
//            throw new SystemException(Code.SYSTEM_TIMEOUT_ERR, "服务器访问超时, 请稍后再试!!");
//        }
        return bookDao.getById(id);
    }

    @Override
    public Book getByName(String name) {
        return bookDao.getByName(name);
    }

    @Override
    public List<Book> getByNameKeyword(String keyword) {
        return bookDao.getByNameKeyword(keyword);
    }

}
