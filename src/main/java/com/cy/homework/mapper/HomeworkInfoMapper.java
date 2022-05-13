package com.cy.homework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cy.homework.entity.Homework;
import com.cy.homework.entity.HomeworkInfo;
import com.cy.homework.vo.CommentVO;
import com.cy.homework.vo.HomeworkInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HomeworkInfoMapper extends BaseMapper<HomeworkInfo> {

    IPage<HomeworkInfoVO> findCommitHomework(IPage<HomeworkInfoVO> page, @Param("studentId")String studentId);

    IPage<HomeworkInfo> findStudentHomework(IPage<HomeworkInfo> page,
                                            @Param("homeworkId")String homeworkId,
                                            @Param("studentName")String studentName,
                                            @Param("teacherId")String teacherId);

    IPage<CommentVO> findCommentHomework(IPage<CommentVO> page,
                                         @Param("homeworkId")String homeworkId,
                                         @Param("course")String course,
                                         @Param("studentId")String studentId);
}
