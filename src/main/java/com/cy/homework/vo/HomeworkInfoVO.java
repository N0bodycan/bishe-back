package com.cy.homework.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class HomeworkInfoVO {

    private Integer id;
    private String homeworkId;
    private String course;
    private String teacherId;
    private Date stime;
    private Date etime;
    private int type;
    private String filename;
    private String text;
    private String info;
    private String pinyu;
    private int score;
}
