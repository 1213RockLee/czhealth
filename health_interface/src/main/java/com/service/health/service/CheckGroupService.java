package com.service.health.service;

import com.service.health.Exception.MyException;
import com.service.health.entity.PageResult;
import com.service.health.entity.QueryPageBean;
import com.service.health.pojo.CheckGroup;

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
