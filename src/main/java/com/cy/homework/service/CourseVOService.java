package com.cy.homework.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.vo.CourseVO;
import org.apache.ibatis.annotations.Param;

public interface CourseVOService {

    IPage<CourseVO> findCoursePage(IPage<CourseVO> page, @Param("name")String name, @Param("teachername")String teachername);
}
