package com.service.health.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReportDao {
    Integer findMember(String date);

    List<Map<String, Object>> findSetmeal();

    Integer findTodayNewMember(String today);

    Integer findThisMonthOrderNumber(@Param("startDate")String firstDayOfThisMonth, @Param("endDate")String lastDayOfThisMonth);

    Integer findTotalMember();

    Integer findThisWeekNewMember(String firstDayOfWeek);

    Integer findThisMonthNewMember(String firstDayOfThisMonth);

    Integer findTodayOrderNumber(String today);

    Integer findTodayVisitsNumber(String today);

    Integer findThisWeekOrderNumber(@Param("startDate")String firstDayOfWeek, @Param("endDate")String lastDayOfWeek);

    Integer findThisWeekVisitsNumber(String firstDayOfWeek);

    Integer findThisMonthVisitsNumber(String firstDayOfThisMonth);

    List<Map<String, Object>> findHotSetmeal();
}
