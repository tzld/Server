package com.example.server.exception;

import com.example.server.enums.ResultEnum;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//使自定义异常捕获函数生效
@RestControllerAdvice
@Slf4j
public class PoiExceptionHandler {
//    标记处理对象
    @ExceptionHandler(PoiException.class)
//    处理异常的函数
    public Result poiExceptionHandler(Exception e){
        log.info("poiExceptionHandler:{}",e);
        if(e.getMessage().endsWith(ResultEnum.ERROR_NOT_FOUND.getMsg())){
            return Result.fail(ResultEnum.ERROR_NOT_FOUND);
        }else if(e.getMessage().endsWith(ResultEnum.ERROR_OPERATION.getMsg())){
            return Result.fail(ResultEnum.ERROR_OPERATION);
        }
//        统一返回
        return Result.fail();

    }
//    漏掉的
    @ExceptionHandler(Exception.class)
    public Result serverErrorHandler(Exception e){
        return Result.fail();
    }
}
