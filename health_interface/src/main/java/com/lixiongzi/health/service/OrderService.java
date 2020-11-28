package com.lixiongzi.health.service;

import com.lixiongzi.health.Exception.MyException;

import java.util.Map;



public interface OrderService {
    int submit(Map map)throws MyException;

}
