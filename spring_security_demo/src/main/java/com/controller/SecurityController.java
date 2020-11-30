package com.controller;

import com.service.health.constant.MessageConstant;
import com.service.health.entity.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {
        @RequestMapping("/add")
        @PreAuthorize("hasAuthority('CHECKITEM_ADD')")//表示用户必须拥有add权限才能调用当前方法
            public Result add()  {
            System.out.println("添加方法成功了");
                return new Result(true, MessageConstant.ADD_CHECKGROUP_FAIL,null);
            }
}
