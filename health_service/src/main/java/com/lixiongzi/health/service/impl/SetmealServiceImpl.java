package com.lixiongzi.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.dao.SetmealDao;
import com.lixiongzi.health.entity.PageResult;
import com.lixiongzi.health.entity.QueryPageBean;
import com.lixiongzi.health.pojo.Setmeal;
import com.lixiongzi.health.service.SetmealService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    @Override
    @Transactional
    public void addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
        //要注意判断是否非空
        setmealDao.addSetmeal(setmeal);
        if (checkgroupIds!=null) {
            for (Integer checkGroupId : checkgroupIds) {
                setmealDao.addSetmealAndCheckGroup(setmeal.getId(),checkGroupId);
            }
        }
    }

    @Override
    public PageResult<Setmeal> findByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<Setmeal> setmealPage=setmealDao.findByPageByCondition(queryPageBean.getQueryString());
        return new PageResult<>(setmealPage.getTotal(),setmealPage.getResult());
    }

    @Override
    public Map findSetmealById(int id) {
        HashMap<String, Object> resultMap = new HashMap<>();
      Setmeal setmeal=setmealDao.findSetmealById(id);
      List<Integer> integerList=setmealDao.findCheckGroupIdsBySetmealById(id);
      resultMap.put("setmeal",setmeal);
      resultMap.put("checkgroupIds",integerList);
        return resultMap;
    }
@Transactional
    @Override
    public void updateSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
        //删除关系
        setmealDao.deleteSetmealAndCheckGroup(setmeal.getId());
      //判断非空,插入关系
        if (checkgroupIds!=null) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealAndCheckGroup(setmeal.getId(),checkgroupId);
            }
        }
        //更新套餐数据
        setmealDao.updateSetmeal(setmeal);
    }
@Transactional
    @Override
    public void deleteSetmealById(int id) {
        //先判断是不是有关系
    if (setmealDao.findCountSetmealAndOrder(id)!=0) {
        throw  new MyException("此套餐已有顾客下单,无法删除");
    }
    setmealDao.deleteSetmealAndCheckGroup(id);
    setmealDao.deleteSetmealById(id);
    }
}
