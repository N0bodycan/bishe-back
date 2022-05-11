package com.cy.homework.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.mapper.CourseVOMapper;
import com.cy.homework.service.CourseVOService;
import com.cy.homework.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseVOServiceImpl implements CourseVOService {

    @Autowired
    CourseVOMapper courseVOMapper;

    @Override
    public IPage<CourseVO> findCoursePage(IPage<CourseVO> page, String name, String teachername) {
        return courseVOMapper.findCoursePage(page, name, teachername);
    }
}
