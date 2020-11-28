package com.lixiongzi.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.constant.MessageConstant;
import com.lixiongzi.health.entity.Result;
import com.lixiongzi.health.service.OrderService;
import com.lixiongzi.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        //要对验证码进行校验
        String token ="tel"+ (String) map.get("telephone");
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get(token);
        if (s==null){
            //为null,也就是redis没有存储,可能是号码错误,提示进行号码的更正
            return new Result(false,"请检查号码",null);
        }
        if (!((String)map.get("validateCode")).equals(jedis.get(token))) {
            //错误就给出提示
            return new Result(false,"验证码错误,请重新验证",null);
        }
        //成功就删除
        jedis.del(token);
        map.put("orderType", "微信预约");
        map.put("orderStatus", "未到诊");
     int id=  orderService.submit(map);
     return new Result(true, "提交订单成功",id );
    }
}
