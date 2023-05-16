package com.ken.stuscoremanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/10 15:07
 * @description 教师信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private Integer id;
    private String teachno;
    private String name;
    private String graclass;
    private String courseName;
    private String psw;
    private String sex;
    private String phone;
    private String qq;
    private String photo;
}
