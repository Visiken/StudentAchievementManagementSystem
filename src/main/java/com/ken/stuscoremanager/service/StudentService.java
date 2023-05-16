package com.ken.stuscoremanager.service;

import com.github.pagehelper.Page;
import com.ken.stuscoremanager.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/10 22:12
 * @description service层
 */
public interface StudentService {

    /**
     * 分页
     * @param offset
     * @param limit
     * @return
     */
    List<Student> findStudentPage(Integer offset, Integer limit);

    /**
     * 页数统计
     * @return
     */
    Integer studentCount();

    /**
     * 查询学生用户是否存在
     * @param stuno
     * @param password
     * @return
     */
    Student studentCheckExist(String stuno, String password);

    /**
     * 分页助手
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Student> findByPageHelper(int pageNum, int pageSize);

    /**
     * 模糊查询
     * @param name
     * @param offset
     * @param limit
     * @return
     */
    List<Student> findByStuName(String name, int offset, int limit);

}
