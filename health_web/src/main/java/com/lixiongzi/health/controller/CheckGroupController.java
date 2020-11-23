package com.lixiongzi.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lixiongzi.health.entity.PageResult;
import com.lixiongzi.health.entity.QueryPageBean;
import com.lixiongzi.health.entity.Result;
import com.lixiongzi.health.pojo.CheckGroup;
import com.lixiongzi.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import static com.lixiongzi.health.constant.MessageConstant.*;
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {
            PageResult pageResult = checkGroupService.findByPage(queryPageBean);
            return new Result(true, QUERY_CHECKGROUP_SUCCESS, pageResult);
    }

    @RequestMapping("/findCheckGroupById")
    public Result findCheckGroupById(int id) {
            CheckGroup checkGroup = checkGroupService.findCheckGroupById(id);
            return new Result(true, QUERY_CHECKGROUP_SUCCESS, checkGroup);
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id) {

            List<Integer> integerList = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true, QUERY_CHECKGROUP_SUCCESS, integerList);

    }

    @RequestMapping("/updateGroup")
    public Result updateGroup(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

            checkGroupService.updateGroup(checkGroup, checkitemIds);
            return new Result(true, "更新检查组信息成功", null);

    }

    @RequestMapping("addCheckGroup")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

            checkGroupService.addCheckGroup(checkGroup, checkitemIds);
            return new Result(true,ADD_CHECKITEM_SUCCESS , null);

    }
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){

            checkGroupService.deleteById(id);
            return new Result(true,DELETE_CHECKGROUP_SUCCESS , null);

        }
}
