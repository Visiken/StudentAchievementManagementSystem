package com.ken.stuscoremanager.dao;

import com.ken.stuscoremanager.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/12 13:30
 * @description 教师的一些功能操作
 */
@Repository
public interface TeacherMapper {

    /**
     * 检查教师信息是否存在
     * @param username
     * @param password
     * @return
     */
    @Select("select * from teacher where teachno=#{username} and psw=#{password}")
    Teacher teacherCheckExist(@Param("username") String username,@Param("password") String password);


    /**
     * 分页
     * @param offset
     * @param limit
     * @return
     */
    @Select("select * from teacher limit #{offset}, #{limit}")
    List<Teacher> findTeachersByPage(@Param("offset") Integer offset,@Param("limit") Integer limit);

    /**
     * 教师统计
     * @return
     */
    @Select("select count(*) from teacher")
    Integer teacherCount();

    /**
     * 教师模糊查询功能
     * @param name
     * @param offset
     * @param limit
     * @return
     */
    @Select("select * from teacher where name like CONCAT('%',#{name},'%') limit #{offset}, #{limit}")
    List<Teacher> findTeachersByNames(@Param("name") String name,@Param("offset") Integer offset,@Param("limit") Integer limit);
}
