package com.ken.stuscoremanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/10 15:01
 * @description 管理员
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Admin {
    private Integer id;
    private String account;
    private String name;
    private String psw;
}
