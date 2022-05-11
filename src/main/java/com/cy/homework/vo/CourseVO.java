package com.cy.homework.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CourseVO {
    @TableId(value = "id")
    private Integer id;
    private String name;
    private Integer score;
    private String teachers;
}
