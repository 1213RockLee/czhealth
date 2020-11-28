package com.lixiongzi.health.service;

import com.lixiongzi.health.pojo.OrderSetting;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface OrderSettingService {
    void upload(List<String[]> orderSettingList) throws ParseException;

}
