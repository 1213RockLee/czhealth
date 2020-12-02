package com.service.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.service.health.Exception.MyException;
import com.service.health.dao.ReportDao;
import com.service.health.service.ReportService;
import com.service.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;

    @Override
    public Map getMemberReport(ArrayList<String> monthList) {
        //在这个地方,遍历集合,拼接上字符串-31,然后查询即可
        List<Integer> integerList = new ArrayList<>();
        for (String s : monthList) {
            s += "-31";
            Integer memberCount = reportDao.findMember(s);
            integerList.add(memberCount);
        }
        HashMap<String, List> resultMap = new HashMap<>();
        resultMap.put("months", monthList);
        resultMap.put("memberCount", integerList);
        return resultMap;
    }

    @Override
    public Map findSetmeal() {
        //查询每一个套餐的名字,以及套餐的个数,但是需要查出来之后迭代map,把map里边的值取出来,分别放到list中
        List<Map<String, Object>> resultMapList = reportDao.findSetmeal();
        HashMap<String, Object> resultMap = new HashMap<>();
        ArrayList<String> setmealNames = new ArrayList<>();
        for (Map<String, Object> nameAndCountMap : resultMapList) {
            String name = (String) nameAndCountMap.get("name");
            setmealNames.add(name);
        }
        resultMap.put("setmealNames", setmealNames);
        resultMap.put("setmealCount", resultMapList);

        return resultMap;
    }

    @Override
    public Map<String,Object> getBusinessReportData() {
        //在这个地方要把所有的参数先生成,sql语句简单,但是,参数挺多的
        //今天
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = sd.format(date);
        String firstDayOfWeek = null;
        String lastDayOfWeek = null;
        String firstDayOfThisMonth = null;
        String lastDayOfThisMonth = null;
        try {
            //这周的周一
            firstDayOfWeek = DateUtils.parseDate2String(DateUtils.getFirstDayOfWeek(date));
            //这周的周末
            lastDayOfWeek = DateUtils.parseDate2String(DateUtils.getLastDayOfWeek(date));
            //本月的月初
            firstDayOfThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            //本月的月末,这个在工具类好像没有,自己加了一个,把获取日历对象,day变1,月+1,日-1
            lastDayOfThisMonth = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());
        } catch (Exception e) {
            throw  new MyException("日期格式转化错误");
        }
        //开始调用方法
        Date reportDate = date;
        Integer todayNewMember = reportDao.findTodayNewMember(today);
        Integer totalMember = reportDao.findTotalMember();
        Integer thisWeekNewMember = reportDao.findThisWeekNewMember(firstDayOfWeek);
        Integer thisMonthNewMember = reportDao.findThisMonthNewMember(firstDayOfThisMonth);
        Integer todayOrderNumber = reportDao.findTodayOrderNumber(today);
        Integer todayVisitsNumber = reportDao.findTodayVisitsNumber(today);
        Integer thisWeekOrderNumber = reportDao.findThisWeekOrderNumber(firstDayOfWeek, lastDayOfWeek);
        Integer thisWeekVisitsNumber = reportDao.findThisWeekVisitsNumber(firstDayOfWeek);
        Integer thisMonthOrderNumber = reportDao.findThisMonthOrderNumber(firstDayOfThisMonth, lastDayOfThisMonth);
        Integer thisMonthVisitsNumber = reportDao.findThisMonthVisitsNumber(firstDayOfThisMonth);
        //接下来是那个获取热门套餐的方法
        //需要获取几个数据,套餐名,
        List<Map<String, Object>> hotSetmeal = reportDao.findHotSetmeal();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("reportDate", reportDate);
        resultMap.put("todayNewMember", todayNewMember);
        resultMap.put("totalMember", totalMember);
        resultMap.put("thisWeekNewMember", thisWeekNewMember);
        resultMap.put("thisMonthNewMember", thisMonthNewMember);
        resultMap.put("todayOrderNumber", todayOrderNumber);
        resultMap.put("todayVisitsNumber", todayVisitsNumber);
        resultMap.put("thisWeekOrderNumber", thisWeekOrderNumber);
        resultMap.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        resultMap.put("thisMonthOrderNumber", thisMonthOrderNumber);
        resultMap.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        resultMap.put("hotSetmeal", hotSetmeal);
        return resultMap;
    }
}
