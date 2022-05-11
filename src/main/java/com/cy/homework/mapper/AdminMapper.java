package com.cy.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.homework.entity.Admin;
import com.cy.homework.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("select username,password from admin where username = #{username}")
    UserVO getUPByName(String username);

    @Select("select name from admin where username=#{username}")
    String getNameByUsername(String username);

    Admin getAdminByUsername(String username);
}
