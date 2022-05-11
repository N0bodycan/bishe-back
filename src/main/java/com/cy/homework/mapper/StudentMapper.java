package com.cy.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.entity.Student;
import com.cy.homework.vo.StudentClazzVO;
import com.cy.homework.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select("select username,password from student where username = #{username}")
    UserVO getUPByName(String username);


    @Select("select name from student where username=#{username}")
    String getNameByUsername(String username);

    IPage<StudentClazzVO> findStudent(IPage<StudentClazzVO> page, @Param("username")String username, @Param("name")String name,@Param("clazz")String clazz);

    @Select("select photo from student where username=#{username}")
    String getPhotoByUsername(String username);

    Integer resetPasswrod(String username,String password);
}
