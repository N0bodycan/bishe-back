package com.cy.homework.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.entity.Student;
import com.cy.homework.entity.Teacher;
import com.cy.homework.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeacherService {

    UserVO getUPByName(String username);

    String getNameByUsername(String username);

    Teacher getTeacherByUsername(String username);

    IPage<Teacher> findTeacherPage(IPage<Teacher> page, @Param("username")String username, @Param("name")String name);
}
