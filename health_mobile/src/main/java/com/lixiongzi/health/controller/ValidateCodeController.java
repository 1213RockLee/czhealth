package com.lixiongzi.health.controller;

import com.aliyuncs.exceptions.ClientException;
import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.constant.MessageConstant;
import com.lixiongzi.health.entity.Result;
import com.lixiongzi.health.pojo.Member;
import com.lixiongzi.health.utils.SMSUtils;
import com.lixiongzi.health.utils.ValidateCodeUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.logging.Logger;

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
