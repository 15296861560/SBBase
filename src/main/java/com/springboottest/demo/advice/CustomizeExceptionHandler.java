package com.springboottest.demo.advice;


import com.alibaba.fastjson.JSON;
import com.springboottest.demo.dto.ResultDTO;
import com.springboottest.demo.exception.CustomizeErrorCode;
import com.springboottest.demo.exception.CustomizeException;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                        HttpServletResponse response,
                        Throwable ex,
                        Model model){
        String contentType=request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO=null;
            //返回json
            if (ex instanceof CustomizeException){
                resultDTO=ResultDTO.errorOf((CustomizeException)ex);
            }else {
                resultDTO=ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer=response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException e){}

//            return new ModelAndView("error");
            return null;

        }else {

            //错误页面跳转
            if (ex instanceof CustomizeException){
                model.addAttribute("errorMessage",ex.getMessage());

            }else {
                model.addAttribute("errorMessage",CustomizeErrorCode.SYS_ERROR);
            }
            return new ModelAndView("error");

        }


    }

}
