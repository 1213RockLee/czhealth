package com.lixiongzi.health.dao;

import com.lixiongzi.health.pojo.Order;
import com.lixiongzi.health.pojo.OrderSetting;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.Date;

public interface OrderSettingDao {

    OrderSetting findOrderSettingByDate(Date date);

    void uploadOrderSetting(OrderSetting orderSetting);

    void addOrderSetting(OrderSetting orderSetting);

    //返回订单,看是不是有这一天
    OrderSetting findSetmealSettingDate(Date orderDate);

    OrderSetting findSetmelSettingReservation(Date orderDate);

    Integer updateReservation(Date date);
}
