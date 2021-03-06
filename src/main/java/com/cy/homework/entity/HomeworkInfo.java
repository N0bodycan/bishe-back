package com.cy.homework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("homeworkinfo")
public class HomeworkInfo {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String homeworkId;
    private String studentId;
    private int type;
    private String filename;
    private String text;
    private String info;
    private String pinyu;
    private int score;
    private int state;
}
