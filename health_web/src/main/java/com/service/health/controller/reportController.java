package com.service.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.http.HttpRequest;
import com.service.health.constant.MessageConstant;
import com.service.health.entity.Result;

import com.service.health.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class reportController {
    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        //在这里将月份就查询出来,然后传入服务层,有几个需要注意的,第一,不需日,只需要月,然后到业务层拼接1和31即可
        //这个就是当前的日历对象了
        Calendar calendar = Calendar.getInstance();
        ArrayList<String> monthList = new ArrayList<>(12);
        //创建要得到的没有日期的格式
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM");
        //减一年
        calendar.add(Calendar.YEAR, -1);
        //然后就准备遍历,月份加一,开始存
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, +1);
            //这里要转成date格式,因为日历格式是没有说可以减掉日期的
            Date date = calendar.getTime();
            String month = sd.format(date);
            monthList.add(month);
        }
        Map resultMap = reportService.getMemberReport(monthList);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, resultMap);
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        Map resultMap = reportService.findSetmeal();
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, resultMap);
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> resultMap = reportService.getBusinessReportData();
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, resultMap);
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        //直接在controller中把数据获取,写到流里面去就ok了
        Map resultMap = reportService.findSetmeal();
        String realPath = request.getSession().getServletContext().getRealPath("/templete/report_template.xlsx");
        //Context context = new PoiContext();
        //context.putVar("obj", reportData);

        return new Result(true, "导出数据成功", null);
    }
        @RequestMapping("/exportPdf")
            public void exportPdf(HttpServletRequest request,HttpServletResponse response)  {
            try {
                //在这个地方要把pdf生成,然后通过流传输即可
                //定义模板源文件
                String resourcePath = request.getSession().getServletContext().getRealPath("D:\\code\\health_parent\\health_web\\src\\main\\webapp\\template\\health_business.jrxml");
                //定义模板编译后的路径
                String destinationPath = request.getSession().getServletContext().getRealPath("D:\\code\\health_parent\\health_web\\src\\main\\webapp\\template\\health_business.jsp");
                //编译模板
                JasperCompileManager.compileReportToFile(resourcePath,destinationPath);
                //构建数据
                Map<String, Object> businessReportData = reportService.getBusinessReportData();
                HashMap<String, String> parameterHashMap = new HashMap<>();
                //参数的设置
                parameterHashMap.put("reportDate",(String)businessReportData.get("reportDate"));
                parameterHashMap.put("todayNewMember",(String)businessReportData.get("todayNewMember"));
                parameterHashMap.put("totalMember",(String)businessReportData.get("totalMember"));
                parameterHashMap.put("thisWeekNewMember",(String)businessReportData.get("thisWeekNewMember"));
                parameterHashMap.put("thisMonthNewMember",(String)businessReportData.get("thisMonthNewMember"));
                parameterHashMap.put("todayOrderNumber",(String)businessReportData.get("todayOrderNumber"));
                parameterHashMap.put("todayVisitsNumber",(String)businessReportData.get("todayVisitsNumber"));
                parameterHashMap.put("thisWeekOrderNumber",(String)businessReportData.get("thisWeekOrderNumber"));
                parameterHashMap.put("thisWeekVisitsNumber",(String)businessReportData.get("thisWeekVisitsNumber"));
                parameterHashMap.put("thisMonthOrderNumber",(String)businessReportData.get("thisMonthOrderNumber"));
                parameterHashMap.put("thisMonthVisitsNumber",(String)businessReportData.get("thisMonthVisitsNumber"));
                //循环的集合的遍历
                List<Map> mapList=new ArrayList<>();
                List hotSetmeal = (List) businessReportData.get("hotSetmeal");
                //填充数据到模板
                //destinationPath是我模板编译后的存放路径,parameterHashMap是我把单个的数据从查询结果取出来,单独生成的一个map,hotsetmeal是我把热门套餐取出来的集合
                JasperPrint print = JasperFillManager.fillReport(destinationPath, parameterHashMap, new JRBeanCollectionDataSource(hotSetmeal));

                //设置响应头
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition","attachment;filename=businessReport.pdf");
                //将文件写入流
                JasperExportManager.exportReportToPdfStream(print,response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }

        }
}
