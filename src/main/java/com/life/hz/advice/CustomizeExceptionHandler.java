package com.life.hz.advice;

import com.life.hz.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {

    /*
    * 拦截异常   exception 包中自定义拦截404 500 等  错误
    *
    * */
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {

//      instanceof  测试它左边的对象是否是它右边的类的实例，返回boolean的数据类型。
        if(e instanceof CustomizeException){
            model.addAttribute("ErrorMessage",e.getMessage());
        }else {
            model.addAttribute("ErrorMessage","服务小哥去追寻诗和远方了...请稍后在试");
        }
        return new ModelAndView("error");
    }



}
