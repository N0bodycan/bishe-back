package com.cy.homework.common;

import com.chenkaiwei.krest.JwtUtil;
import com.chenkaiwei.krest.exceptions.KrestTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = null;
        try {
            authorization = request.getHeader("Authorization").replace("Bearer ","");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        log.info(authorization);
        log.info("进入过滤器");

        if (authorization == null || authorization == ""){
            throw new KrestTokenException("Token异常或过期");
        }
        if(JwtUtil.isExpire(authorization)){
            throw new KrestTokenException("Token异常或过期");
        }
        return true;
    }
}
