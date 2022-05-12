package com.cy.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.entity.Student;
import com.cy.homework.entity.Teacher;
import com.cy.homework.vo.ClazzVO;
import com.cy.homework.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

    @Select("select username,password from teacher where username = #{username}")
    UserVO getUPByName(String username);


    @Select("select name from teacher where username=#{username}")
    String getNameByUsername(String username);

    Teacher getTeacherByUsername(String username);

    IPage<Teacher> findTeacherPage(IPage<Teacher> page, @Param("username")String username, @Param("name")String name);

    @Select("select photo from teacher where username=#{username}")
    String getPhotoByUsername(String username);

    Integer resetPasswrod(String username,String password);

    IPage<Teacher> findClazzTeacher(IPage<Teacher> page,@Param("username")String username, @Param("name")String name,@Param("clazz")String clazz);

    List<Teacher> getTeacherByTitle(@Param("id")String id);

    @Select("SELECT clazz_id,clazz_name FROM `clazz` WHERE teacher_id = #{teacherId} GROUP BY clazz_id")
    ClazzVO[] getTeacherClazz(@Param("teacherId")String teacherId);
}
