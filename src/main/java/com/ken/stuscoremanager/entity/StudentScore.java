package com.ken.stuscoremanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/10 15:16
 * @description 学生成绩
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentScore {
    private Integer id;
    private String stuno;
    private String courseid;
    private Float score;
    private String type;
}
