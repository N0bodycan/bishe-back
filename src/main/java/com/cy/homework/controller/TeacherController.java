package com.cy.homework.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;
import com.cy.homework.common.Result;
import com.cy.homework.dto.PwdDTO;
import com.cy.homework.entity.Homework;
import com.cy.homework.entity.HomeworkInfo;
import com.cy.homework.entity.Teacher;
import com.cy.homework.mapper.HomeworkInfoMapper;
import com.cy.homework.mapper.HomeworkMapper;
import com.cy.homework.mapper.TeacherMapper;
import com.cy.homework.service.TeacherService;
import com.cy.homework.vo.ClazzVO;
import com.cy.homework.vo.StudentClazzVO;
import com.cy.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    HomeworkMapper homeworkMapper;

    @Autowired
    HomeworkInfoMapper homeworkInfoMapper;

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

    @PostMapping("/changepwd")
    public Result<?> changepwd(@RequestBody PwdDTO pwdDTO){
        String op = SecureUtil.md5(pwdDTO.getOp());
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",pwdDTO.getId());
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        if (!teacher.getPassword().equals(op)){
            return Result.error(400,"原密码错误");
        }else{
            UpdateWrapper<Teacher> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("username", pwdDTO.getId());
            Teacher t = new Teacher().setPassword(SecureUtil.md5(pwdDTO.getNp()));
            int update = teacherMapper.update(t, updateWrapper);
            if (update>0){
                return Result.OK("修改密码成功！请重新登录",null);
            }else {
                return Result.error("未知错误！");
            }
        }
    }

    @GetMapping
    public Result<?> getTeacher(@RequestParam("id")String id){
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", id);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        if (teacher!= null){
            return Result.OK(teacher);
        }else {
            return Result.error(400,"错误");
        }
    }

    @GetMapping("/clazz")
    public Result<?> getTeacherClazz(@RequestParam("id")String id){
        ClazzVO[] clazz = teacherMapper.getTeacherClazz(id);
        return Result.OK(clazz);
    }

    @PutMapping("/publish")
    public Result<?> publishHomework(@RequestBody Homework homework){
        if (homework.getStime().before(new Date())){
            homework.setState(1);
        }
//        System.out.println(homework);
        int insert = homeworkMapper.insert(homework);
        if (insert>0 ){
            return Result.OK("发布作业成功！");
        }else {
            return Result.error(400,"发布作业失败！");
        }
    }

    @PutMapping("/homeworkupdate")
    public Result<?> updateHomework(@RequestBody Homework homework){
//        System.out.println(homework);
        int insert = homeworkMapper.updateById(homework);
        if (insert>0 ){
            return Result.OK("修改成功！");
        }else {
            return Result.error(400,"修改失败！");
        }
    }

    @GetMapping("/homework/page")
    public Result<?> getStudentPage(@RequestParam("size")Integer size,
                                    @RequestParam(value = "clazzId",required = false)String clazzId,
                                    @RequestParam(value = "type",required = false)String type,
                                    @RequestParam(value = "teacherId")String teacherId){
        Map map = new HashMap();
        Page<Homework> page = new Page<>(1,size);
        IPage<Homework> homeworks = homeworkMapper.findHomework(page, clazzId, type,teacherId);
        map.put("data",homeworks.getRecords());
//        System.out.println(studentpage.getRecords());
        map.put("total", homeworks.getTotal());

        return Result.OK(map);
    };

    @GetMapping("/homework/page/{index}")
    public Result<?> getStudentPageIndex(@PathVariable("index") Integer index,
                                         @RequestParam(value = "clazzId",required = false)String clazzId,
                                         @RequestParam(value = "type",required = false)String type,
                                         @RequestParam(value = "teacherId")String teacherId){
        System.out.println(clazzId+"/"+type+"/"+teacherId);
        Map map = new HashMap();
        Page<Homework> page = new Page<>(index,5);
        IPage<Homework> homeworks = homeworkMapper.findHomework(page, clazzId, type,teacherId);
//        System.out.println(homeworks.getRecords());
        map.put("data",homeworks.getRecords());
        map.put("total", homeworks.getTotal());
        return Result.OK(map);
    }

    @GetMapping("/studenthomework/page")
    public Result<?> getStudentHomeworkPage(@RequestParam("size")Integer size,
                                    @RequestParam(value = "homeworkId",required = false)String homeworkId,
                                    @RequestParam(value = "studentName",required = false)String studentName,
                                    @RequestParam(value = "teacherId")String teacherId){
        Map map = new HashMap();
        Page<HomeworkInfo> page = new Page<>(1,size);
        IPage<HomeworkInfo> homeworks = homeworkInfoMapper.findStudentHomework(page, homeworkId, studentName,teacherId);
        map.put("data",homeworks.getRecords());
//        System.out.println(studentpage.getRecords());
        map.put("total", homeworks.getTotal());

        return Result.OK(map);
    };

    @GetMapping("/studenthomework/page/{index}")
    public Result<?> getStudentHomeworkPageIndex(@PathVariable("index") Integer index,
                                                 @RequestParam(value = "homeworkId",required = false)String homeworkId,
                                                 @RequestParam(value = "studentName",required = false)String studentName,
                                                 @RequestParam(value = "teacherId")String teacherId){
        Map map = new HashMap();
        Page<HomeworkInfo> page = new Page<>(index,5);
        IPage<HomeworkInfo> homeworks = homeworkInfoMapper.findStudentHomework(page, homeworkId, studentName,teacherId);
//        System.out.println(homeworks.getRecords());
        map.put("data",homeworks.getRecords());
        map.put("total", homeworks.getTotal());
        return Result.OK(map);
    }


    @DeleteMapping("/homework/{homeworkId}")
    public Result<?> delete(@PathVariable("homeworkId")Integer homeworkId){
        int i = homeworkMapper.deleteById(homeworkId);
        if (i>0){
            return Result.OK("删除成功！");
        }else {
            return Result.error(400,"删除失败！");
        }
    }

    @PutMapping("/comment")
    public Result<?> comment(@RequestBody HomeworkInfo homeworkInfo){
        int i = homeworkInfoMapper.updateById(homeworkInfo);
        if (i>0){
            return Result.OK("评阅成功！");
        }else {
            return Result.error(400,"评阅失败！");
        }
    }
}
