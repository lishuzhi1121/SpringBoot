package com.quvideo.service;

import com.quvideo.domain.Book;

import java.util.List;

public interface BookService {
    /**
     * 保存图书
     * @param book Pojo对象
     * @return true 保存成功, false 保存失败
     */
    boolean save(Book book);

    /**
     * 删除图书
     * @param id 要删除的图书ID
     * @return true 删除成功, false 删除失败
     */
    boolean delete(Integer id);

    /**
     * 更新图书信息
     * @param book 要更新的Pojo对象
     * @return true 更新成功, false 更新失败
     */
    boolean update(Book book);

    /**
     * 查询所有图书
     * @return 所有图书Pojo对象列表
     */
    List<Book> getAll();

    /**
     * 根据ID查询单个图书
     * @param id 图书ID
     * @return 图书Pojo对象
     */
    Book getById(Integer id);

    /**
     * 根据名称查询单个图书信息
     * @param name 完整图书名称
     * @return 图书Pojo对象
     */
    Book getByName(String name);

    /**
     * 根据名称模糊匹配所有图书信息
     * @param keyword 名称关键字
     * @return 匹配到的图书Pojo对象列表
     */
    List<Book> getByNameKeyword(String keyword);
}
