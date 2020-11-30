package com.service.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.service.health.constant.MessageConstant;
import com.service.health.entity.Result;
import com.service.health.pojo.OrderSetting;
import com.service.health.service.OrderSettingService;
import com.service.health.utils.DateUtils;
import com.service.health.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) throws Exception {
        System.out.println("收到请求了");
        //将上传的文件读取,存到List集合
        List<String[]> orderSettingList = new ArrayList<>();
        orderSettingList = POIUtils.readExcel(excelFile);
        orderSettingService.upload(orderSettingList);
        return new Result(true, MessageConstant.UPLOAD_SUCCESS, null);
    }

    @RequestMapping("/findOrderSettingByMonth")
    public Result findOrderSettingByMonth(String month) {
        List<Map<String, Object>> orderSettingList = orderSettingService.findOrderSettingByMonth(month);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, orderSettingList);
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody Map map) throws Exception {
        //传入之后判断一下,预约人数大于自己的即将传入的数据的话,那就要报错,否则直接更新即可
        Date orderDate = DateUtils.parseString2Date((String) map.get("orderDate"), "yyyy-MM-dd");
        map.put("orderDate",orderDate);
        orderSettingService.editNumberByDate(map);
        return new Result(true, "编辑数据成功", null);
    }
}
