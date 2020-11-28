package com.lixiongzi.health.dao;

import com.lixiongzi.health.pojo.Order;
import com.lixiongzi.health.pojo.OrderSetting;

import java.util.Date;

public interface OrderDao {
    Order findSameOrder(Order order);

    void addOrder(Order order);
}
