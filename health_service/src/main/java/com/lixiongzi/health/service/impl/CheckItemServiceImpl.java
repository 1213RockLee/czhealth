package com.lixiongzi.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.dao.CheckGroupDao;
import com.lixiongzi.health.dao.CheckItemDao;
import com.lixiongzi.health.entity.PageResult;
import com.lixiongzi.health.entity.QueryPageBean;
import com.lixiongzi.health.pojo.CheckItem;
import com.lixiongzi.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    public List<CheckItem> findAll(){
        //查询所有检查项
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        //使用helper来查询分页信息
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if (queryPageBean.getQueryString()!=null) {
            //如果有查询条件的话，就拼接上百分号
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<CheckItem> checkItemPage= checkItemDao.findByCondition(queryPageBean.getQueryString());
        List<CheckItem> rows=checkItemPage.getResult();
        long total=checkItemPage.getTotal();
        return new PageResult(total,rows);
    }

    @Override
    public void deleteById(int id) throws MyException {
        if (checkItemDao.findCheckItemAndCheckGroup(id)==0) {
            checkItemDao.deleteById(id);
        }else
            {
                throw new MyException("此检查项被检查组使用,无法删除");
            }

    }

    @Override
    public void updateCheckItem(CheckItem checkItem) {
        checkItemDao.updateCheckItem(checkItem);
    }

    @Override
    public CheckItem findCheckItemById(Integer id) {
        return checkItemDao.findCheckItemById(id);
    }

}
