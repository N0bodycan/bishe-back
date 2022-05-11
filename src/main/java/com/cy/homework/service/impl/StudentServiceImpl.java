package com.cy.homework.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.entity.Student;
import com.cy.homework.mapper.StudentMapper;
import com.cy.homework.service.StudentService;
import com.cy.homework.vo.StudentClazzVO;
import com.cy.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public UserVO getUPByName(String username) {
        return studentMapper.getUPByName(username);
    }

    @Override
    public String getNameByUsername(String username) {
        return studentMapper.getNameByUsername(username);
    }

    @Override
    public IPage<StudentClazzVO> findStudent(IPage<StudentClazzVO> page, String username, String name,String clazz) {
        return studentMapper.findStudent(page, username, name,clazz);
    }
}
