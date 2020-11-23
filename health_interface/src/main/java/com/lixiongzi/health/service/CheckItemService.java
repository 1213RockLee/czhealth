package com.lixiongzi.health.service;

import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.entity.PageResult;
import com.lixiongzi.health.entity.QueryPageBean;
import com.lixiongzi.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    PageResult findByPage(QueryPageBean queryPageBean);

    void deleteById(int id);

    void updateCheckItem(CheckItem checkItem);

    CheckItem findCheckItemById(Integer id) throws MyException;
}
