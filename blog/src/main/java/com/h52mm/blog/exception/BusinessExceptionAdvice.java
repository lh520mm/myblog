package com.h52mm.blog.exception;

import com.h52mm.blog.commons.BlogResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class BusinessExceptionAdvice {

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public BlogResponse defaultException(HttpServletRequest request, BusinessException e){
        BlogResponse response= BlogResponse.newInstance();
        response.setErrorCode("500");
        response.setMessage(e.getMessage());
        response.setSuccess(false);
        return response;
    }

}
