package com.lixiongzi.health.service;

import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.entity.PageResult;
import com.lixiongzi.health.entity.QueryPageBean;
import com.lixiongzi.health.pojo.Setmeal;

import java.util.Map;

public interface SetmealService {
    void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult<Setmeal> findByPage(QueryPageBean queryPageBean);

    Map findSetmealById(int id);

    void updateSetmeal(Setmeal setmeal, Integer[] checkgroupIds);

    void deleteSetmealById(int id) throws MyException;
}
