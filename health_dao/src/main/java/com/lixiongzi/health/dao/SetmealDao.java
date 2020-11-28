package com.lixiongzi.health.dao;

import com.github.pagehelper.Page;
import com.lixiongzi.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {
    void addSetmeal(Setmeal setmeal);

    public void addSetmealAndCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkGroupId")Integer checkGroupId);

    Page<Setmeal> findByPageByCondition(String queryString);

    Setmeal findSetmealById(int id);

    List<Integer> findCheckGroupIdsBySetmealById(int id);

    void deleteSetmealAndCheckGroup(Integer id);

    void updateSetmeal(Setmeal setmeal);

    int findCountSetmealAndOrder(int id);

    void deleteSetmealById(int id);

    List<Setmeal> findAllSetmeal();

    Setmeal findDetailById(int id);
}
