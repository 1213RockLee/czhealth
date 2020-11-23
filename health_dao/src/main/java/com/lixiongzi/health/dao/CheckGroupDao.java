package com.lixiongzi.health.dao;

import com.github.pagehelper.Page;
import com.lixiongzi.health.entity.PageResult;
import com.lixiongzi.health.entity.QueryPageBean;
import com.lixiongzi.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    Page<CheckGroup> findByPage(String queryString);

    CheckGroup findCheckGroupById(int id);

    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    void deleteGroupAndItemByGroupId(Integer id);

    void updateGroup(CheckGroup checkGroup);


    void addGroupAndItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkItemId") Integer checkItemId);

    void addCheckGroup(CheckGroup checkGroup);

    int findCheckGroupAndSetMeal(int id);



    void deleteGroupById(Integer id);
}
