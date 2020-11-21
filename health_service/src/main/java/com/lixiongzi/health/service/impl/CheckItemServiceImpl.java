package com.lixiongzi.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lixiongzi.health.dao.CheckItemDao;
import com.lixiongzi.health.pojo.CheckItem;
import com.lixiongzi.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public List<CheckItem> findAll(){
        //查询所有检查项
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }
}
