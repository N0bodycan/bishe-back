package com.cy.homework.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cy.homework.common.Result;
import com.cy.homework.entity.Course;
import com.cy.homework.mapper.CourseMapper;
import com.cy.homework.mapper.CourseVOMapper;
import com.cy.homework.service.CourseVOService;
import com.cy.homework.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CourseController {

    @Autowired
    CourseVOService courseVOService;

    @Autowired
    CourseVOMapper courseVOMapper;

    @Autowired
    CourseMapper courseMapper;

    @GetMapping("/admin/coursem/page")
    public Result<?> getTeacherPage(@RequestParam("size")Integer size,
                                    @RequestParam(value = "name",required = false)String name,
                                    @RequestParam(value = "teachername",required = false)String teachername){
        Map map = new HashMap();
        Page<CourseVO> page = new Page<>(1,size);
        IPage<CourseVO> studentpage = courseVOService.findCoursePage(page, name, teachername);
        map.put("data",studentpage.getRecords());
        map.put("total", studentpage.getTotal());

        return Result.OK(map);
    };

    @GetMapping("/admin/coursem/page/{index}")
    public Result<?> getTeacherPageIndex(@PathVariable("index") Integer index,
                                         @RequestParam(value = "name",required = false)String name,
                                         @RequestParam(value = "teachername",required = false)String teachername){
        Map map = new HashMap();
        Page<CourseVO> page = new Page<>(index,5);
        IPage<CourseVO> studentpage = courseVOService.findCoursePage(page, name, teachername);
        map.put("data",studentpage.getRecords());
        map.put("total", studentpage.getTotal());
        return Result.OK(map);
    }

    @GetMapping("/admin/clazzcoursem/page")
    public Result<?> getCCPage(@RequestParam("size")Integer size,
                                    @RequestParam(value = "name",required = false)String name,
                                    @RequestParam(value = "teachername",required = false)String teachername,
                               @RequestParam("clazz")String clazz){
        Map map = new HashMap();
        Page<CourseVO> page = new Page<>(1,size);
        IPage<CourseVO> studentpage = courseVOMapper.findClazzCoursePage(page, name, teachername,clazz);
        map.put("data",studentpage.getRecords());
        map.put("total", studentpage.getTotal());

        return Result.OK(map);
    };

    @GetMapping("/admin/clazzcoursem/page/{index}")
    public Result<?> getCCPageIndex(@PathVariable("index") Integer index,
                                         @RequestParam(value = "name",required = false)String name,
                                         @RequestParam(value = "teachername",required = false)String teachername,
                                    @RequestParam("clazz")String clazz){
        Map map = new HashMap();
        Page<CourseVO> page = new Page<>(index,5);
        IPage<CourseVO> studentpage = courseVOMapper.findClazzCoursePage(page, name, teachername,clazz);
        map.put("data",studentpage.getRecords());
        map.put("total", studentpage.getTotal());
        return Result.OK(map);
    }


    @PutMapping("/admin/coursem")
    public Result<?> addCourse(@RequestBody Course course){
//        System.out.println(course);
        int insert = courseMapper.insert(course);
        if (insert>0){
            return Result.OK("添加成功");
        }
        else {
            return Result.error(400,"添加失败");
        }
    }

    @DeleteMapping("/admin/coursem/{id}")
    public Result<?> deleteCourse(@PathVariable("id")Integer id){
        int i = courseMapper.deleteById(id);
        if (i>0){
            return Result.OK("删除成功！");
        }else {
            return Result.error("删除失败！");
        }

    }

    @GetMapping("/admin/coursem/all")
    public Result<?> getCourseAll(){
        QueryWrapper<Course> c = new QueryWrapper<>();
        List<Course> courses = courseMapper.selectList(c);
//        System.out.println(courses);
        return Result.OK(courses);
    }
}
