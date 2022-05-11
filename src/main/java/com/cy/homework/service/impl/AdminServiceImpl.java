package com.cy.homework.service.impl;

import com.cy.homework.entity.Admin;
import com.cy.homework.mapper.AdminMapper;
import com.cy.homework.service.AdminService;
import com.cy.homework.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public UserVO getUPByName(String username) {
        return adminMapper.getUPByName(username);
    }

    @Override
    public String getNameByUsername(String username) {
        return adminMapper.getNameByUsername(username);
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.getAdminByUsername(username);
    }
}
