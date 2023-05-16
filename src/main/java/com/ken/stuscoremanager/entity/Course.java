package com.ken.stuscoremanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/10 15:12
 * @description 课程
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {
    private Integer id;
    private String courseid;
    private String coursename;
}
