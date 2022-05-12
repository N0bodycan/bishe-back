package com.cy.homework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cy.homework.common.Result;
import com.cy.homework.dto.ClazzDTO;
import com.cy.homework.dto.CourseTeacherDTO;
import com.cy.homework.entity.Clazz;
import com.cy.homework.mapper.ClazzMapper;
import com.cy.homework.service.ClazzService;
import com.cy.homework.vo.ClazzVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClazzController {

    @Autowired
    ClazzService clazzService;
    @Autowired
    ClazzMapper clazzMapper;

    @RequestMapping("/admin/allclazz")
    public Result<?> getAllClazz(){
        ClazzVO[] clazz = clazzService.getAllClazz();
        return Result.OK(clazz);
    }

    @PutMapping("/admin/clazzm/addCC")
    public Result<?> addClazzCourse(@RequestBody CourseTeacherDTO courseTeacherDTO){
        Clazz clazz = new Clazz().setClazzId(courseTeacherDTO.getClazz())
                        .setCourseId(courseTeacherDTO.getCourse())
                        .setTeacherId(courseTeacherDTO.getTeacher())
                        .setClazzName(clazzMapper.getClazzName(courseTeacherDTO.getClazz()));
        int insert = clazzMapper.insert(clazz);
        if (insert>0 ){
            return Result.OK("添加成功");
        }else {
            return Result.error(400,"添加失败！");
        }
    }

    @DeleteMapping("/admin/clazzcourse/{clazzId}/{courseId}")
    public Result<?> deleteClazzCourse(@PathVariable("courseId")String courseId,
                                       @PathVariable("clazzId")String clazzId){
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId).eq("clazz_id",clazzId);
        int delete = clazzMapper.delete(queryWrapper);
        if (delete>0){
            return Result.OK("删除成功!");
        }else {
            return Result.error("删除失败！");
        }
    }

    @PutMapping("/admin/clazzm/add")
    public Result<?> addClazz(@RequestBody ClazzDTO clazzDTO){
        Clazz clazz = new Clazz().setClazzId(clazzDTO.getClazzId()).setClazzName(clazzDTO.getClazzName());
        int insert = clazzMapper.insert(clazz);
        if (insert>0){
            return Result.OK("添加成功");
        }else {
            return Result.error(400,"添加失败！");
        }
    }
}
