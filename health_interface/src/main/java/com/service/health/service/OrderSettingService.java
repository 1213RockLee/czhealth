package com.service.health.service;

import com.service.health.Exception.MyException;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void upload(List<String[]> orderSettingList) throws ParseException;

    List<Map<String,Object>> findOrderSettingByMonth(String month);

    void editNumberByDate(Map map)throws MyException;
}
