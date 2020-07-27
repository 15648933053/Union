package com.starcpdk.edu.service_base.exceptionhandler;

import com.starcpdk.edu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j//输出异常信息到文件加次注解
public class GlobalExceptionHandler {
    //处理全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody//为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //处理特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//为了返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }

    //自定义异常
    @ExceptionHandler(MyException.class)
    @ResponseBody//为了返回数据
    public R error(MyException e){
        e.printStackTrace();
        log.error(e.getMsg());//将日常信息写到error日志信息中
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
