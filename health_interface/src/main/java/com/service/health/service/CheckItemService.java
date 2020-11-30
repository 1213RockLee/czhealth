package com.service.health.service;

import com.service.health.Exception.MyException;
import com.service.health.entity.PageResult;
import com.service.health.entity.QueryPageBean;
import com.service.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    PageResult findByPage(QueryPageBean queryPageBean);

    void deleteById(int id)throws MyException;

    void updateCheckItem(CheckItem checkItem);

    CheckItem findCheckItemById(Integer id) ;
}
