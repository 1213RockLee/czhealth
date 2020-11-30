package com.service.health.dao;

import com.service.health.pojo.Order;

public interface OrderDao {
    Order findSameOrder(Order order);

    void addOrder(Order order);
}
