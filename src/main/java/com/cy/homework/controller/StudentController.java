package com.cy.homework.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;
import com.cy.homework.common.Result;
import com.cy.homework.entity.Homework;
import com.cy.homework.entity.HomeworkInfo;
import com.cy.homework.entity.Student;
import com.cy.homework.mapper.HomeworkInfoMapper;
import com.cy.homework.mapper.HomeworkMapper;
import com.cy.homework.service.StudentService;
import com.cy.homework.vo.CommentVO;
import com.cy.homework.vo.HomeworkInfoVO;
import com.cy.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    HomeworkMapper homeworkMapper;

    @Autowired
    HomeworkInfoMapper homeworkInfoMapper;

    @PostMapping("/login")
    public Result<?> login(@RequestBody UserVO userInput) throws Exception {

        Map res = new HashMap();
        UserVO user = studentService.getUPByName(userInput.getUsername());
        if (user != null) {
            String name = studentService.getNameByUsername(user.getUsername());
            if (SecureUtil.md5(userInput.getPassword()).equals(user.getPassword())) {
                //TODO ↑你自己的验证规则
                JwtUser jwtUser = new JwtUser(user.getUsername(), Arrays.asList("student"));
                res.put("token", KrestUtil.createJwtTokenByUser(jwtUser));
                //↑ 关键是这一步，将token返回给客户端以供后续请求时验证身份。
//                String s = JSON.toJSONString(res);
                res.put("code",1);
                res.put("message", "login success");
                res.put("name", name);
                res.put("username", user.getUsername());
                res.put("userType", 1);
                res.put("isLogin", true);
            } else {
                res.put("code",0);
                res.put("message", "密码错误");
                res.put("islogin", false);
            }

        } else {
            res.put("code",2);
            res.put("isLogin", false);
            res.put("message", "此用户不存在");
            // throw new KrestAuthenticationException("登录失败");
        }
        return Result.OK(res);
    }

    @GetMapping("/homework/page")
    public Result<?> getStudentPage(@RequestParam("size")Integer size,
                                    @RequestParam(value = "course",required = false)String course,
                                    @RequestParam(value = "teacherName",required = false)String teacherName,
                                    @RequestParam(value = "studentId")String studentId){
        UpdateWrapper<Homework> wrapper1 = new UpdateWrapper<>();
        wrapper1.lt("stime",new Date());
        UpdateWrapper<Homework> wrapper2 = new UpdateWrapper<>();
        wrapper2.lt("etime",new Date());
        Homework h = new Homework().setState(1);
        Homework h2 = new Homework().setState(2);
        homeworkMapper.update(h,wrapper1);
        homeworkMapper.update(h2,wrapper2);
        Map map = new HashMap();
        Page<Homework> page = new Page<>(1,size);
        IPage<Homework> homeworks = homeworkMapper.findStudentHomework(page, course, teacherName,studentId);
        map.put("data",homeworks.getRecords());
//        System.out.println(studentpage.getRecords());
        map.put("total", homeworks.getTotal());

        return Result.OK(map);
    };

    @GetMapping("/homework/page/{index}")
    public Result<?> getStudentPageIndex(@PathVariable("index") Integer index,
                                         @RequestParam(value = "course",required = false)String course,
                                         @RequestParam(value = "teacherName",required = false)String teacherName,
                                         @RequestParam(value = "studentId")String studentId){
        UpdateWrapper<Homework> wrapper1 = new UpdateWrapper<>();
        wrapper1.lt("stime",new Date());
        UpdateWrapper<Homework> wrapper2 = new UpdateWrapper<>();
        wrapper2.lt("etime",new Date());
        Homework h = new Homework().setState(1);
        Homework h2 = new Homework().setState(2);
        homeworkMapper.update(h,wrapper1);
        homeworkMapper.update(h2,wrapper2);
        Map map = new HashMap();
        Page<Homework> page = new Page<>(index,5);
        IPage<Homework> homeworks = homeworkMapper.findStudentHomework(page, course, teacherName,studentId);
//        System.out.println(homeworks.getRecords());
        map.put("data",homeworks.getRecords());
        map.put("total", homeworks.getTotal());
        return Result.OK(map);
    }

    @GetMapping("/homework/historypage")
    public Result<?> getStudentHistoryPage(@RequestParam("size")Integer size,
                                    @RequestParam(value = "course",required = false)String course,
                                    @RequestParam(value = "teacherName",required = false)String teacherName,
                                    @RequestParam(value = "studentId")String studentId){

        Map map = new HashMap();
        Page<Homework> page = new Page<>(1,size);
        IPage<Homework> homeworks = homeworkMapper.findStudentHistoryHomework(page, course, teacherName,studentId);
        map.put("data",homeworks.getRecords());
//        System.out.println(studentpage.getRecords());
        map.put("total", homeworks.getTotal());

        return Result.OK(map);
    };

    @GetMapping("/homework/historypage/{index}")
    public Result<?> getStudentHistoryPageIndex(@PathVariable("index") Integer index,
                                         @RequestParam(value = "course",required = false)String course,
                                         @RequestParam(value = "teacherName",required = false)String teacherName,
                                         @RequestParam(value = "studentId")String studentId){
        Map map = new HashMap();
        Page<Homework> page = new Page<>(index,5);
        IPage<Homework> homeworks = homeworkMapper.findStudentHistoryHomework(page, course, teacherName,studentId);
//        System.out.println(homeworks.getRecords());
        map.put("data",homeworks.getRecords());
        map.put("total", homeworks.getTotal());
        return Result.OK(map);
    }

    @GetMapping("/homework/commitpage")
    public Result<?> getStudentCommitPage(@RequestParam("size")Integer size,
                                           @RequestParam(value = "studentId")String studentId){

        Map map = new HashMap();
        Page<HomeworkInfoVO> page = new Page<>(1,size);
        IPage<HomeworkInfoVO> homeworks = homeworkInfoMapper.findCommitHomework(page,studentId);
        map.put("data",homeworks.getRecords());
//        System.out.println(studentpage.getRecords());
        map.put("total", homeworks.getTotal());

        return Result.OK(map);
    };

    @GetMapping("/homework/commitpage/{index}")
    public Result<?> getStudentCommitPageIndex(@PathVariable("index") Integer index,
                                                @RequestParam(value = "studentId")String studentId){
        Map map = new HashMap();
        Page<HomeworkInfoVO> page = new Page<>(index,5);
        IPage<HomeworkInfoVO> homeworks = homeworkInfoMapper.findCommitHomework(page,studentId);
//        System.out.println(homeworks.getRecords());
        map.put("data",homeworks.getRecords());
        map.put("total", homeworks.getTotal());
        return Result.OK(map);
    }


    @GetMapping("/homework/commentpage")
    public Result<?> getStudentCommentPage(@RequestParam("size")Integer size,
                                          @RequestParam("homeworkId")String homeworkId,
                                          @RequestParam("course")String course,
                                          @RequestParam(value = "studentId")String studentId){

        Map map = new HashMap();
        Page<CommentVO> page = new Page<>(1,size);
        IPage<CommentVO> homeworks = homeworkInfoMapper.findCommentHomework(page,homeworkId,course,studentId);
        map.put("data",homeworks.getRecords());
//        System.out.println(studentpage.getRecords());
        map.put("total", homeworks.getTotal());

        return Result.OK(map);
    };

    @GetMapping("/homework/commentpage/{index}")
    public Result<?> getStudentCommentPageIndex(@PathVariable("index") Integer index,
                                                @RequestParam("homeworkId")String homeworkId,
                                                @RequestParam("course")String course,
                                               @RequestParam(value = "studentId")String studentId){
        Map map = new HashMap();
        Page<CommentVO> page = new Page<>(index,5);
        IPage<CommentVO> homeworks = homeworkInfoMapper.findCommentHomework(page,homeworkId,course,studentId);
//        System.out.println(homeworks.getRecords());
        map.put("data",homeworks.getRecords());
        map.put("total", homeworks.getTotal());
        return Result.OK(map);
    }


    @PutMapping("/homework/submit")
    public Result<?> submitHomework(@RequestBody HomeworkInfo homeworkInfo){
//        System.out.println(homeworkInfo);
        int insert = homeworkInfoMapper.insert(homeworkInfo);
        if (insert>0){
            return Result.OK("提交作业成功！");
        }else {
            return Result.error(400,"提交作业失败！");
        }
    }

    @PostMapping("/homework/update")
    public Result<?> updateHomework(@RequestBody HomeworkInfo homeworkInfo) {
//        System.out.println(homeworkInfo);
        int i = homeworkInfoMapper.updateById(homeworkInfo);
        if (i>0){
            return Result.OK("修改成功！");
        }else {
            return Result.error(400,"修改失败！");
        }
    }
}
