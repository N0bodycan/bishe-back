package com.cy.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.entity.Homework;
import com.cy.homework.vo.StudentClazzVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HomeworkMapper extends BaseMapper<Homework> {

    IPage<Homework> findHomework(IPage<Homework> page, @Param("clazzId")String clazzId, @Param("type")String type, @Param("teacherId")String teacherId);

    IPage<Homework> findStudentHomework(IPage<Homework> page, @Param("course")String course, @Param("teacherName")String teacherName, @Param("studentId")String studentId);

    IPage<Homework> findStudentHistoryHomework(IPage<Homework> page, @Param("course")String course, @Param("teacherName")String teacherName, @Param("studentId")String studentId);
}
