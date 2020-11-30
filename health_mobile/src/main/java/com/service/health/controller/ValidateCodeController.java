package com.service.health.controller;

import com.service.health.Exception.MyException;
import com.service.health.constant.MessageConstant;
import com.service.health.entity.Result;
import com.service.health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        String token = "tel" + telephone;
        String validateCode = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
        Jedis jedis = jedisPool.getResource();
        if (jedis.get(token) == null) {
            //没有找到就发送,并存入redis
            try {
                //SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode);
                System.out.println(validateCode+"***"+telephone);
                jedis.setex(token,60*10,validateCode);
                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS, null);
            } catch (Exception e) {
               throw new MyException(MessageConstant.SEND_VALIDATECODE_FAIL);
            } finally {
                jedis.close();
            }
            }else {
            return new Result(false,"已经发送了",null);
        }
    }
}
