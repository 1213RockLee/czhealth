package com.lixiongzi.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lixiongzi.health.constant.MessageConstant;
import com.lixiongzi.health.entity.Result;
import com.lixiongzi.health.pojo.Setmeal;
import com.lixiongzi.health.service.SetmealService;
import com.lixiongzi.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class MobileController {
    @Reference
    private SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result findAllSetmeal() {
        List<Setmeal> setmealList = setmealService.findAllSetmeal();
        for (Setmeal setmeal : setmealList) {
            setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        }
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealList);
    }

    @RequestMapping("/findDetailById")
    public Result findDetailById(int id) {
        Setmeal setmeal = setmealService.findDetailById(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }
        @RequestMapping("/findById")
            public Result findSetmealById(int id)  {
            List<Setmeal> setmealList = setmealService.findAllSetmeal();
            for (Setmeal setmeal : setmealList) {
                if (setmeal.getId()==id) {
                    setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
                    return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
                }
            }
               return  null;
            }

}
