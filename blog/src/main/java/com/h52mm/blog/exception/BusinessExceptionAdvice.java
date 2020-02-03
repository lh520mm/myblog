package com.h52mm.blog.exception;

import com.h52mm.blog.commons.WechatResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class BusinessExceptionAdvice {

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public WechatResponse defaultException(HttpServletRequest request, BusinessException e){
        WechatResponse response=WechatResponse.newInstance();
        response.setErrorCode("500");
        response.setMessage(e.getMessage());
        response.setSuccess(false);
        return response;
    }

}
