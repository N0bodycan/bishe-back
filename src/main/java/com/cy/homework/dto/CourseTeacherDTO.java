package com.cy.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseTeacherDTO {

    private Integer clazz;
    private Integer course;
    private String teacher;
}
