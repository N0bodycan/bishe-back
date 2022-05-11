package com.cy.homework.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.mapper.ClazzMapper;
import com.cy.homework.service.ClazzService;
import com.cy.homework.vo.ClazzVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    ClazzMapper clazzMapper;

    @Override
    public ClazzVO[] getAllClazz() {
        return clazzMapper.getAllClazz();
    }

    @Override
    public IPage<ClazzVO> findClazz(IPage<ClazzVO> page, String clazzName) {
        return clazzMapper.findClazz(page, clazzName);
    }
}
