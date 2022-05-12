package com.cy.homework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Homework {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String course;
    private String clazzId;
    private String teacherId;
    private Date stime;
    private Date etime;
    private Integer type;
    private String filename;
    private String text;
    private int state;

}
