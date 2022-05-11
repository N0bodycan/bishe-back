package com.cy.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.entity.Clazz;
import com.cy.homework.vo.ClazzVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClazzMapper extends BaseMapper<Clazz> {

    @Select("SELECT clazz_id,clazz_name FROM `clazz` GROUP BY clazz_id")
    ClazzVO[] getAllClazz();

    @Select("select clazz_name from clazz where clazz_id = #{clazzId} GROUP BY clazz_id")
    String getClazzName(@Param("clazzId")Integer clazzId);

    IPage<ClazzVO> findClazz(IPage<ClazzVO> page, @Param("clazzName")String clazzName);

    Integer pullClazzTeacher(@Param("clazz")String clazz,@Param("username")String username);
}
