package com.ken.stuscoremanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/10 15:03
 * @description 学生信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    private String stuno;
    private String name;
    private String psw;
    private String sex;
    private String phone;
    private String qq;
    private String photo;
}
