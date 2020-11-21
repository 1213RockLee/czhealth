package com.lixiongzi.health.dao;

import com.lixiongzi.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);
}
