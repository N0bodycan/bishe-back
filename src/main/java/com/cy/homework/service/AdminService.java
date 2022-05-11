package com.cy.homework.service;

import com.cy.homework.entity.Admin;
import com.cy.homework.vo.UserVO;

public interface AdminService {

    UserVO getUPByName(String username);

    String getNameByUsername(String username);

    Admin getAdminByUsername(String username);
}
