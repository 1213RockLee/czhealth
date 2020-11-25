package com.lixiongzi.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lixiongzi.health.dao.OrderSettingDao;
import com.lixiongzi.health.pojo.OrderSetting;
import com.lixiongzi.health.service.OrderSettingService;
import com.lixiongzi.health.service.SetmealService;
import com.lixiongzi.health.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    @Transactional
    public void upload(List<String[]> orderSettingList) throws ParseException {
        //获得日期字符串的格式
        SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
        Date date=null;
        OrderSetting orderSetting=new OrderSetting();
        //遍历集合
        for (String[] strings : orderSettingList) {
            //第一个数据是日期,第二个是可预约数量
              //取出来的是一个字符串,要把它变成日期

                Integer number = Integer.valueOf(strings[1]);
                date = sdf.parse(strings[0]);
                orderSetting.setOrderDate(date);
                orderSetting.setNumber(number);
            if (orderSettingDao.findOrderSettingByDate(date)!=null) {
                //不为0,那就是更新数据
                orderSettingDao.uploadOrderSetting(orderSetting);
            }else {
                //为0,那就是插入数据
                orderSettingDao.addOrderSetting(orderSetting);
            }
        }
    }
}
