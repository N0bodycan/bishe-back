package com.cy.homework.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommentVO {

    private Integer id;
    private Integer homeworkId;
    private String teacherName;
    private String course;
    private Integer type;
    private String filename;
    private String text;
    private String info;
    private String pinyu;
    private String score;

}
