package com.cy.homework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class HomeworkInfo {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Homework homework;
    private Student student;
    private int type;
    private String filename;
    private String text;
    private String info;
    private String pinyu;
    private int filescore;
    private int textscore;
    private int score;

}
