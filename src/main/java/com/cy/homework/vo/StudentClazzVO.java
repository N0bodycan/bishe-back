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
public class StudentClazzVO {
    @TableId(value = "id")
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String clazz;
    private String gender;
    private String photo;
    private int unreadinfo;
}

