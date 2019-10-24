package com.life.hz.advice;

import com.alibaba.fastjson.JSON;
import com.life.hz.dto.ResultDTO;
import com.life.hz.exception.CustomizeException;
import com.life.hz.exception.CustomizeExceptionCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    /*
    * 拦截异常   exception 包中自定义拦截404 500 等  错误
    *
    * */
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model , HttpServletResponse response) {

        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResultDTO resultDTO;
            //返回json
            if(e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf( (CustomizeException) e);
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeExceptionCode.SYS_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e1) {

            }
            return null;

        }else {

            //错误页面跳
            // instanceof  测试它左边的对象是否是它右边的类的实例，返回boolean的数据类型。
            if(e instanceof CustomizeException){
                model.addAttribute("ErrorMessage",e.getMessage());
            }else {
                model.addAttribute("ErrorMessage",CustomizeExceptionCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
