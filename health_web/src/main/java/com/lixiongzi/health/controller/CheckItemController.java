package com.lixiongzi.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.entity.PageResult;
import com.lixiongzi.health.entity.QueryPageBean;
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

            List<CheckItem> checkItemList = checkItemService.findAll();
            return new Result(true,QUERY_CHECKITEM_SUCCESS,checkItemList);

    }
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){

            checkItemService.add(checkItem);
            return new Result(true,ADD_CHECKITEM_SUCCESS,null);

    }
    @RequestMapping("/findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean){

            PageResult pageResult=  checkItemService.findByPage(queryPageBean);
            return new Result(true,QUERY_CHECKITEM_SUCCESS,pageResult);

    }
    @RequestMapping("/deleteById")
    public Result deleteById(int id) {

            checkItemService.deleteById(id);
            return new Result(true,DELETE_CHECKITEM_SUCCESS,null);

    }

@RequestMapping("/updateCheckItem")
    public Result updateCheckItem(@RequestBody CheckItem checkItem){

        checkItemService.updateCheckItem(checkItem);
        return new Result(true,EDIT_CHECKITEM_SUCCESS,null);

}
@RequestMapping("/findCheckItemById")
    public Result findCheckItemById(Integer id){

      CheckItem checkItem= checkItemService.findCheckItemById(id);
        return new Result(true,QUERY_CHECKITEM_SUCCESS,checkItem);

}
}
