package com.cy.homework.controller;

import com.cy.homework.common.Result;
import com.cy.homework.entity.Student;
import com.cy.homework.mapper.StudentMapper;
import com.cy.homework.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    StudentMapper studentMapper;

    @GetMapping("/status")
    public Result<?> loginState(){
        Map res = new HashMap();
        res.put("isLogin", true);
        return Result.OK(res);
    }

    @GetMapping("/photo")
    public Result<?> getPhoto(@RequestParam("username")String username,@RequestParam("role")String role){
        Map res = new HashMap();
        res.put("photo","");
        if ("1".equals(role)){
            String photo = studentMapper.getPhotoByUsername(username);
            res.put("photo",photo);
        }
        if ("2".equals(role)){
            String photo = teacherMapper.getPhotoByUsername(username);
            res.put("photo",photo);
        }
        return Result.OK(res);
    }
}
