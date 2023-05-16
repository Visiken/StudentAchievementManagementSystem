package com.ken.stuscoremanager.service;

import com.ken.stuscoremanager.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/12 13:33
 * @description 教师服务层
 */
public interface TeacherService {
    /**
     * 检查教师是否存在
     * @param username
     * @param password
     * @return
     */
    Teacher teacherCheckExist(String username, String password);

    /**
     * 分页
     * @param offset
     * @param limit
     * @return
     */
    List<Teacher> findTeachersByPage(Integer offset,Integer limit);

    /**
     * 教师统计
     * @return
     */
    Integer teacherCount();

    /**
     * 教师模糊查询功能
     * @param name
     * @param offset
     * @param limit
     * @return
     */
    List<Teacher> findTeachersByNames(String name,Integer offset,Integer limit);

}
