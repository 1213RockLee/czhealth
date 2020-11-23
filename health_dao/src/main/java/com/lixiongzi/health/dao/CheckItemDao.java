package com.lixiongzi.health.dao;

import com.github.pagehelper.Page;
import com.lixiongzi.health.entity.PageResult;
import com.lixiongzi.health.entity.QueryPageBean;
import com.lixiongzi.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);


    void deleteById(int id);
    Page<CheckItem> findByCondition(String queryString);

    void updateCheckItem(CheckItem id);

    CheckItem findCheckItemById(Integer id);

    Integer findCheckItemAndCheckGroup(int id);
}
