package com.cy.homework.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.vo.ClazzVO;
import org.apache.ibatis.annotations.Param;

public interface ClazzService {

    ClazzVO[] getAllClazz();

    IPage<ClazzVO> findClazz(IPage<ClazzVO> page, @Param("clazzName")String clazzName);
}
