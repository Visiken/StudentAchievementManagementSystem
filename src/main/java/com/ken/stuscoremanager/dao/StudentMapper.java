package com.ken.stuscoremanager.dao;

import cn.hutool.db.PageResult;
import com.github.pagehelper.Page;
import com.ken.stuscoremanager.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/10 15:50
 * @description 学生操作接口
 */
@Repository
public interface StudentMapper {
    /**
     * 分页查询学生信息（手动分页版本）
     * @param offset 偏移量，第一页：（0,5）第二页（5,5） 第三页(10,5)
     * @param limit 一次查询记录的条数
     * @return
     * -- 分页查询limit
     * -- 简介
     * -- 分页查询在项目开发中常见，由于数据量很大，显示屏长度有限，因此对数据需要采取分页显示方式。例如数据共有30条，每页显示1-5条，第二页显示6-10条
     * -- 格式
     * -- 方式1-显示前n条
     * select 字段1，字段2... from 表名 limit n
     * -- 方式2-分页显示
     * select 字段1，字段2... from 表名 limit m，n
     * -- m：整数，表示从第几条索引开始，计算方式（当前页-1）*每页显示条数
     * -- n：整数，表示查询多少条数据
     * -- 操作
     * -- 查询product表的前5条记录
     * select * from product limit 5
     * -- 从第4条开始显示，显示5条
     * select * from product limit 3,5;
     */
    @Select("select * from stu limit #{offset}, #{limit} ")
    List<Student> findStudentPage(@Param("offset") Integer offset, @Param("limit") Integer limit);


    /**
     * 统计学生人数
     * @return
     */
    @Select("select count(*) from stu")
    Integer studentCount();

    /**
     * 查询学生用户是否存在
     * @param stuno
     * @param password
     * @return
     */
    @Select("select * from stu where stuno=#{stuno} and psw=#{password}")
    Student studentCheckExist(@Param("stuno") String stuno,@Param("password") String password);

    /**
     * 使用（分页助手）分页
     * @param pageNum
     * @param pageSize
     * @return 返回结果为list集合
     */
    @Select("SELECT * FROM stu")
    Page<Student> findByPageHelper(int pageNum, int pageSize);

    /**
     * 学生模糊查询查询功能
     * @param name
     * @param offset
     * @param limit
     * @return 使用like进行模糊查询
     */
    @Select("select * from stu where name like CONCAT('%',#{name},'%') limit #{offset}, #{limit}")
    List<Student> findByStuName(@Param("name") String name,@Param("offset") int offset,@Param("limit") int limit);

}
