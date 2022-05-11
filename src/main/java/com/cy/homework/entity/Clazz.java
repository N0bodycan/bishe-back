package com.cy.homework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Clazz {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer clazzId;
    private String clazzName;
    private String semesterId;
    private Integer courseId;
    private String teacherId;
}
