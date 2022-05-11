package com.cy.homework.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.homework.entity.Student;
import com.cy.homework.vo.StudentClazzVO;
import com.cy.homework.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface StudentService {

    UserVO getUPByName(String username);

    String getNameByUsername(String username);

    IPage<StudentClazzVO> findStudent(IPage<StudentClazzVO> page, @Param("username")String username, @Param("name")String name,@Param("clazz")String clazz);
}
