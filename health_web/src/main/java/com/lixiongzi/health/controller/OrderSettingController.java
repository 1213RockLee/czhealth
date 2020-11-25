package com.lixiongzi.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lixiongzi.health.constant.MessageConstant;
import com.lixiongzi.health.entity.Result;
import com.lixiongzi.health.service.OrderSettingService;
import com.lixiongzi.health.utils.POIUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;
        @RequestMapping("/upload")
            public Result upload(MultipartFile excelFile) throws Exception {
            System.out.println("收到请求了");
            //将上传的文件读取,存到List集合
            List<String[]> orderSettingList =new ArrayList<>();
             orderSettingList = POIUtils.readExcel(excelFile);
            orderSettingService.upload(orderSettingList);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS,null);
            }
}
