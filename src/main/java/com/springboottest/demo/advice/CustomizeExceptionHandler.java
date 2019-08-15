package com.springboottest.demo.advice;


import com.springboottest.demo.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model){
//        HttpStatus status=getStatus(request);
        if (ex instanceof CustomizeException){
            model.addAttribute("errorMessage",ex.getMessage());

        }else {
            model.addAttribute("errorMessage","服务器出问题了，要不然稍后再试一下吧...");
        }
        return new ModelAndView("error");

    }
//    private HttpStatus getStatus(HttpServletRequest request){
//        Integer statusCode=(Integer)request.getAttribute("java.servlet.error.status_code");
//        if (statusCode==null){
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }
}
