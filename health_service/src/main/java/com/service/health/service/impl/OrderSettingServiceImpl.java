package com.service.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.service.health.Exception.MyException;
import com.service.health.dao.OrderSettingDao;
import com.service.health.pojo.OrderSetting;
import com.service.health.service.OrderSettingService;
import com.service.health.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Map<String,Object>> findOrderSettingByMonth(String month) {
        //调用dao的方法查询该月份的预约的数据
        month=month+"%";
     List<Map<String,Object>> orderSettingList=  orderSettingDao.findOrderSettingByMonth(month);
     //在这个
        return orderSettingList;
    }

    @Override
    public void editNumberByDate(Map map) throws MyException{
        //传入之后判断一下,预约人数大于自己的即将传入的数据的话,那就要报错,否则直接更新即可,可以使用上面写的方法

        Date orderDate = (Date)map.get("orderDate");
        Integer number = Integer.valueOf((String)map.get("number"));
        Integer reservations = orderSettingDao.findReservations(orderDate);
        //判断这个reservations和number的大小,有问题抛出异常,没问题的话就直接更新即可
        if (number<reservations) {
            throw new MyException("最大预约人数不可以小于已预约人数");
        }
        //正常,直接更新数据就可以
        orderSettingDao.updateNumber(number,orderDate);
    }
}
