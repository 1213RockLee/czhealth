package com.lixiongzi.health.dao;

import com.lixiongzi.health.pojo.OrderSetting;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.Date;

public interface OrderSettingDao {
    OrderSetting findOrderSettingByDate(Date date);
    void uploadOrderSetting(OrderSetting orderSetting);
    void addOrderSetting(OrderSetting orderSetting);
}
