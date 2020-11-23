package com.lixiongzi.health.controller;

import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionAdvice {
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException exception){
        return new Result(false,exception.getMessage(),null);
    }
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception exception){
        return new Result(false,"服务器内部发生未知错误",null);
    }
}
