package com.cy.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.entity.Course;
import com.cy.homework.entity.Student;
import com.cy.homework.vo.CourseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourseVOMapper extends BaseMapper<CourseVO> {

    IPage<CourseVO> findCoursePage(IPage<CourseVO> page, @Param("name")String name, @Param("teachername")String teachername);

IPage<CourseVO> findClazzCoursePage(IPage<CourseVO>page,@Param("name")String name, @Param("teachername")String teachername,@Param("clazz")String clazz);
}
