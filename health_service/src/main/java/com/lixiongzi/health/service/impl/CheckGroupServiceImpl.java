package com.lixiongzi.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.dao.CheckGroupDao;
import com.lixiongzi.health.entity.PageResult;
import com.lixiongzi.health.entity.QueryPageBean;
import com.lixiongzi.health.pojo.CheckGroup;
import com.lixiongzi.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        //利用helper进行操作
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        if (queryPageBean.getQueryString() != null) {
            //如果有查询条件的话，就拼接上百分号
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<CheckGroup> checkGroupPage = checkGroupDao.findByPage(queryPageBean.getQueryString());
        List<CheckGroup> rows = checkGroupPage.getResult();
        long total = checkGroupPage.getTotal();
        return new PageResult(total, rows);
    }

    @Override
    public CheckGroup findCheckGroupById(int id) {
        return checkGroupDao.findCheckGroupById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void updateGroup(CheckGroup checkGroup, Integer[] checkitemIds) {
//这个要先调用方法删除，再调用方法添加即可
        checkGroupDao.deleteGroupAndItemByGroupId(checkGroup.getId());
        checkGroupDao.updateGroup(checkGroup);
        for (int i = 0; i < checkitemIds.length; i++) {
            checkGroupDao.addGroupAndItem(checkGroup.getId(),checkitemIds[i]);
        }

    }

    @Override
    public void addCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds) {
        //先添加，获取id
        checkGroupDao.addCheckGroup(checkGroup);
        //遍历数组
        for (int i = 0; i < checkitemIds.length; i++) {
            checkGroupDao.addGroupAndItem(checkGroup.getId(), checkitemIds[i]);

        }
    }

    @Override
    public void deleteById(Integer id)throws MyException {
        if (checkGroupDao.findCheckGroupAndSetMeal(id)==0) {
            //没有关系,可以删除
            checkGroupDao.deleteGroupAndItemByGroupId(id);
            checkGroupDao.deleteGroupById(id);
        }else {
            throw new MyException("此检查组被套餐使用,无法删除");
        }
    }
}
