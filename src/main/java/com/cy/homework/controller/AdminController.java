package com.cy.homework.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenkaiwei.krest.KrestUtil;
import com.chenkaiwei.krest.entity.JwtUser;

import com.chenkaiwei.krest.exceptions.KrestTokenException;
import com.cy.homework.common.Result;
import com.cy.homework.dto.PwdDTO;
import com.cy.homework.entity.*;
import com.cy.homework.mapper.*;
import com.cy.homework.service.AdminService;
import com.cy.homework.service.ClazzService;
import com.cy.homework.service.StudentService;
import com.cy.homework.service.TeacherService;
import com.cy.homework.vo.ClazzVO;
import com.cy.homework.vo.StudentClazzVO;
import com.cy.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    AdminService adminService;

    @Autowired
    StudentService studentService;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TeacherService teacherService;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    ClazzStudentMapper clazzStudentMapper;

    @Autowired
    ClazzService clazzService;

    @Autowired
    ClazzMapper clazzMapper;

    @PostMapping("/login")
    public Result<?> login(@RequestBody UserVO userInput) throws Exception {

        Map res=new HashMap();
        UserVO user = adminService.getUPByName(userInput.getUsername());
        if (user!=null){
            String name = adminService.getNameByUsername(user.getUsername());
            if (SecureUtil.md5(userInput.getPassword()).equals(user.getPassword())) {
            //TODO ???????????????????????????
                JwtUser jwtUser=new JwtUser(user.getUsername(), Arrays.asList("admin"));
                res.put("token", KrestUtil.createJwtTokenByUser(jwtUser));
                //??? ????????????????????????token??????????????????????????????????????????????????????
                res.put("code", 1);
                res.put("message","login success");
                res.put("name",name);
                res.put("username",user.getUsername());
                res.put("userType", 3);
                res.put("isLogin",true);
            }else{
                res.put("code", 0);
                res.put("message","????????????");
                res.put("islogin",false);
            }

        }else{
            res.put("code", 2);
            res.put("message","??????????????????");
            res.put("islogin",false);
            // throw new KrestAuthenticationException("????????????");
        }
        return Result.OK(res);
    }

    @PostMapping("/changepwd")
    public Result<?> changepwd(@RequestBody PwdDTO pwdDTO){
        String op = SecureUtil.md5(pwdDTO.getOp());
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",pwdDTO.getId());
        Admin admin = adminMapper.selectOne(queryWrapper);
        if (!admin.getPassword().equals(op)){
            return Result.error(400,"???????????????");
        }else{
            UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("username", pwdDTO.getId());
            Admin a = new Admin().setPassword(SecureUtil.md5(pwdDTO.getNp()));
            int update = adminMapper.update(a, updateWrapper);
            if (update>0){
                return Result.OK("????????????????????????????????????",null);
            }else {
                return Result.error("???????????????");
            }
        }
    }

    @GetMapping
    public Result<?> test(){
        Map<String,String> res=new HashMap<>();
        res.put("token",KrestUtil.createNewJwtTokenIfNeeded());
//        throw new KrestTokenException("token??????");
        return Result.OK(res);
    }

    @GetMapping("/studentm/page")
    public Result<?> getStudentPage(@RequestParam("size")Integer size,
                             @RequestParam(value = "username",required = false)String username,
                             @RequestParam(value = "name",required = false)String name,
                             @RequestParam(value = "clazz",required = false)String clazz){
        Map map = new HashMap();
        Page<StudentClazzVO> page = new Page<>(1,size);
        IPage<StudentClazzVO> studentpage = studentService.findStudent(page, username, name,clazz);
        map.put("data",studentpage.getRecords());
//        System.out.println(studentpage.getRecords());
        map.put("total", studentpage.getTotal());

        return Result.OK(map);
    };

    @GetMapping("/studentm/page/{index}")
    public Result<?> getStudentPageIndex(@PathVariable("index") Integer index,
                               @RequestParam(value = "username",required = false)String username,
                               @RequestParam(value = "name",required = false)String name,
                               @RequestParam(value = "clazz",required = false)String clazz){
        Map map = new HashMap();
        Page<StudentClazzVO> page = new Page<>(index,5);
        IPage<StudentClazzVO> studentpage = studentService.findStudent(page, username, name,clazz);
        map.put("data",studentpage.getRecords());
        map.put("total", studentpage.getTotal());
        return Result.OK(map);
    }

    @GetMapping("/teacherm/page")
    public Result<?> getTeacherPage(@RequestParam("size")Integer size,
                             @RequestParam(value = "username",required = false)String username,
                             @RequestParam(value = "name",required = false)String name){
        Map map = new HashMap();
        Page<Teacher> page = new Page<>(1,size);
        IPage<Teacher> studentpage = teacherService.findTeacherPage(page, username, name);
        map.put("data",studentpage.getRecords());
        map.put("total", studentpage.getTotal());

        return Result.OK(map);
    };

    @GetMapping("/teacherm/page/{index}")
    public Result<?> getTeacherPageIndex(@PathVariable("index") Integer index,
                               @RequestParam(value = "username",required = false)String username,
                               @RequestParam(value = "name",required = false)String name){
        Map map = new HashMap();
        Page<Teacher> page = new Page<>(index,5);
        IPage<Teacher> studentpage = teacherService.findTeacherPage(page, username, name);
        map.put("data",studentpage.getRecords());
        map.put("total", studentpage.getTotal());
        return Result.OK(map);
    }

    @GetMapping("/teacherm/clazzpage")
    public Result<?> getClazzTeacherPage(@RequestParam("size")Integer size,
                                    @RequestParam(value = "username",required = false)String username,
                                    @RequestParam(value = "name",required = false)String name,
                                     @RequestParam(value = "clazz")String clazz){
        Map map = new HashMap();
        Page<Teacher> page = new Page<>(1,size);
        IPage<Teacher> studentpage = teacherMapper.findClazzTeacher(page, username, name,clazz);
        map.put("data",studentpage.getRecords());
        map.put("total", studentpage.getTotal());
        return Result.OK(map);
    };

    @GetMapping("/teacherm/clazzpage/{index}")
    public Result<?> getClazzTeacherPageIndex(@PathVariable("index") Integer index,
                                         @RequestParam(value = "username",required = false)String username,
                                         @RequestParam(value = "name",required = false)String name,
                                         @RequestParam(value = "clazz")String clazz){
        Map map = new HashMap();
        Page<Teacher> page = new Page<>(index,5);
        IPage<Teacher> studentpage = teacherMapper.findClazzTeacher(page, username, name,clazz);
        map.put("data",studentpage.getRecords());
        map.put("total", studentpage.getTotal());
        return Result.OK(map);
    }


    @GetMapping("/clazzm/page")
    public Result<?> getTeacherPage(@RequestParam("size")Integer size,
                                    @RequestParam(value = "clazzName",required = false)String clazzName){
        Map map = new HashMap();
        Page<ClazzVO> page = new Page<>(1,size);
        IPage<ClazzVO> studentpage = clazzService.findClazz(page, clazzName);
        map.put("data",studentpage.getRecords());
        map.put("total", studentpage.getTotal());

        return Result.OK(map);
    };

    @GetMapping("/clazzm/page/{index}")
    public Result<?> getTeacherPageIndex(@PathVariable("index") Integer index,
                                         @RequestParam(value = "clazzName",required = false)String clazzName){
        Map map = new HashMap();
        Page<ClazzVO> page = new Page<>(index,5);
        IPage<ClazzVO> studentpage = clazzService.findClazz(page, clazzName);
        map.put("data",studentpage.getRecords());
        map.put("total", studentpage.getTotal());
        return Result.OK(map);
    }

    @GetMapping("/studentm/exist")
    public Result<?> isStudentExist(@RequestParam("username") String username){
        String name = studentMapper.getNameByUsername(username);
        if (name == "" || name == null){
           return Result.OK();
        }else {
            return Result.error(400,"?????????????????????");
        }
    }

    @GetMapping("/teacherm/exist")
    public Result<?> isTeacherExist(@RequestParam("username") String username){
        String name = teacherService.getNameByUsername(username);
        if (name == "" || name == null){
            return Result.OK();
        }else {
            return Result.error(400,"???????????????????????????");
        }
    }




    /**
     * ????????????
     */
    @PutMapping("/studentm/add")
    public Result<?> addStudent(@RequestBody StudentClazzVO student) {
        String name = studentMapper.getNameByUsername(student.getUsername());
        Map res = new HashMap();
        if (name == null || name.equals("")) {
            Student stu = new Student()
                    .setUsername(student.getUsername())
                    .setGender(student.getGender())
                    .setName(student.getName())
                    .setPassword(SecureUtil.md5(student.getPassword()))
                    .setPhone(student.getPhone())
                    .setPhoto(student.getPhoto());
            int insert0 = studentMapper.insert(stu);
            ClazzStudent cs = new ClazzStudent().setStudentId(student.getUsername()).setClazzId(student.getClazz());
            int insert1 = clazzStudentMapper.insert(cs);

            if (insert0 > 0 && insert1>0) {
                res.put("code", "1");
                res.put("message", "????????????!");
            } else {
                res.put("code", "0");
                res.put("message", "????????????!");
            }
        } else {
            res.put("code", "2");
            res.put("message", "??????????????????!?????????????????????");
        }
        return Result.OK(res);
    }

    @PutMapping("/teacherm/add")
    public Result<?> addTeacher(@RequestBody Teacher teacher) {
        String name = teacherService.getNameByUsername(teacher.getUsername());
        Map res = new HashMap();
        if (name == null || name.equals("")) {
            teacher.setPassword(SecureUtil.md5(teacher.getPassword()));
            int insert = teacherMapper.insert(teacher);
            if (insert > 0) {
                res.put("code", "1");
                res.put("message", "????????????!");
            } else {
                res.put("code", "0");
                res.put("message", "????????????!");
            }
        } else {
            res.put("code", "2");
            res.put("message", "??????????????????!?????????????????????");
        }
        return Result.OK(res);
    }


    @PostMapping("/studentm/resetp/{username}")
    public Result<?> resetSPassword(@PathVariable("username")String username){
        Integer integer = studentMapper.resetPasswrod(username, SecureUtil.md5(username));
        if (integer > 0){
            return Result.OK();
        }else {
            return Result.error(400,"???????????????");
        }
    }

    @PostMapping("/studentm/delete/{username}")
    public Result<?> deleteStudent(@PathVariable("username")String username){
        QueryWrapper<Student> s = new QueryWrapper<>();
        s.eq("username",username);
        int delete = studentMapper.delete(s);

        QueryWrapper<ClazzStudent> c = new QueryWrapper<>();
        c.eq("student_id", username);
        int delete1 = clazzStudentMapper.delete(c);
        if (delete>0 && delete1>0){
            return Result.OK("???????????????");
        }else{
            return Result.error(400,"???????????????");
        }
    }

    @PostMapping("/teacherm/resetp/{username}")
    public Result<?> resetTPassword(@PathVariable("username")String username){
        Integer integer = teacherMapper.resetPasswrod(username, SecureUtil.md5(username));
        if (integer > 0){
            return Result.OK();
        }else {
            return Result.error(400,"???????????????");
        }
    }

    @PostMapping("/teacherm/pull/{clazz}/{username}")
    public Result<?> pullClazzTeacher(@PathVariable(value = "clazz")String clazz,@PathVariable("username")String username){
        Integer integer = clazzMapper.pullClazzTeacher(clazz, username);
        if (integer>0){
            return Result.OK("??????????????????!");
        }else {
            return Result.error(400,"??????????????????!");
        }
    }

    @GetMapping("/teacherm/getC")
    public Result<?> getTeacherByTitle(@RequestParam("course")String course){
        List<Teacher> teachers = teacherMapper.getTeacherByTitle(course);
        return Result.OK(teachers);
    }
}
