package com.lixiongzi.health.service;

import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.entity.PageResult;
import com.lixiongzi.health.entity.QueryPageBean;
import com.lixiongzi.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    PageResult findByPage(QueryPageBean queryPageBean);

    CheckGroup findCheckGroupById(int id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void updateGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteById(Integer id)throws MyException;

    List<CheckGroup> findAll();
}
