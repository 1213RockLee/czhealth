package com.service.health.service;

import com.service.health.Exception.MyException;
import com.service.health.entity.PageResult;
import com.service.health.entity.QueryPageBean;
import com.service.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult<Setmeal> findByPage(QueryPageBean queryPageBean);

    Map findSetmealById(int id);

    void updateSetmeal(Setmeal setmeal, Integer[] checkgroupIds);

    void deleteSetmealById(int id) throws MyException;

    List<Setmeal> findAllSetmeal();

    Setmeal findDetailById(int id);
}
