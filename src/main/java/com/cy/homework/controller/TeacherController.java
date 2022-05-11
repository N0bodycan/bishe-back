package com.cy.homework.controller;

import cn.hutool.crypto.SecureUtil;
import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;
import com.cy.homework.common.Result;
import com.cy.homework.service.TeacherService;
import com.cy.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody UserVO userInput) throws Exception {

        Map res = new HashMap();
        UserVO user = teacherService.getUPByName(userInput.getUsername());
        if (user != null) {
            String name = teacherService.getNameByUsername(user.getUsername());
            if (SecureUtil.md5(userInput.getPassword()).equals(user.getPassword())) {
                //TODO ↑你自己的验证规则
                JwtUser jwtUser = new JwtUser(user.getUsername(), Arrays.asList("teacher"));
                res.put("token", KrestUtil.createJwtTokenByUser(jwtUser));
                //↑ 关键是这一步，将token返回给客户端以供后续请求时验证身份。
//                String s = JSON.toJSONString(res);
                res.put("code", 1);
                res.put("message", "login success");
                res.put("name", name);
                res.put("username", user.getUsername());
                res.put("userType", 2);
                res.put("isLogin", true);
            } else {
                res.put("code", 0);
                res.put("message", "密码错误");
                res.put("islogin", false);
            }

        } else {
            res.put("code", 2);
            res.put("message", "此用户不存在");
            res.put("islogin", false);
            // throw new KrestAuthenticationException("登录失败");
        }
        return Result.OK(res);
    }

}