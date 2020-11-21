package com.lixiongzi.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lixiongzi.health.entity.Result;
import com.lixiongzi.health.pojo.CheckItem;
import com.lixiongzi.health.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.List;

import static com.lixiongzi.health.constant.MessageConstant.*;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckItem> checkItemList = checkItemService.findAll();
            return new Result(true,QUERY_CHECKITEM_SUCCESS,checkItemList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,QUERY_CHECKITEM_FAIL,null);
        }
    }
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
            return new Result(true,ADD_CHECKITEM_SUCCESS,null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,ADD_CHECKITEM_FAIL,null);
        }
    }
}
