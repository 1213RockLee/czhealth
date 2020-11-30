package com.service.health.service;

import com.service.health.Exception.MyException;

import java.util.Map;



public interface OrderService {
    int submit(Map map)throws MyException;

}
