package com.cy.homework.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.entity.Student;
import com.cy.homework.entity.Teacher;
import com.cy.homework.mapper.TeacherMapper;
import com.cy.homework.service.TeacherService;
import com.cy.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public UserVO getUPByName(String username) {
        return teacherMapper.getUPByName(username);
    }

    @Override
    public String getNameByUsername(String username) {
        return teacherMapper.getNameByUsername(username);
    }

    @Override
    public Teacher getTeacherByUsername(String username) {
        return teacherMapper.getTeacherByUsername(username);
    }

    @Override
    public IPage<Teacher> findTeacherPage(IPage<Teacher> page, String username, String name) {
        return teacherMapper.findTeacherPage(page,username,name);
    }
}
