package com.lixiongzi.health.service;

import com.lixiongzi.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);
}
