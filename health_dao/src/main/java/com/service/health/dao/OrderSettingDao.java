package com.service.health.dao;

import com.service.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {

    OrderSetting findOrderSettingByDate(Date date);

    void uploadOrderSetting(OrderSetting orderSetting);

    void addOrderSetting(OrderSetting orderSetting);

    //返回订单,看是不是有这一天
    OrderSetting findSetmealSettingDate(Date orderDate);

    OrderSetting findSetmelSettingReservation(Date orderDate);

    Integer updateReservation(Date date);

    List<Map<String,Object>> findOrderSettingByMonth(String month);

    Integer findReservations(Date date);

    void updateNumber(@Param("number") Integer number, @Param("orderDate") Date date);
}
