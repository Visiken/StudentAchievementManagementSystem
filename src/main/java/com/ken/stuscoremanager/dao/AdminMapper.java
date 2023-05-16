package com.ken.stuscoremanager.dao;

import com.ken.stuscoremanager.entity.Admin;
import com.ken.stuscoremanager.entity.Student;
import com.ken.stuscoremanager.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/12 13:32
 * @description 管理员的一些方法
 */
@Repository
public interface AdminMapper {

    @Select("select * from admin where account=#{username} and psw=#{password}")
    Admin adminCheckExist(String username, String password);

    /**
     * 插入数据时一定要指明参数
     * @param student
     * @return
     */
    @Select("insert into stu(stuno, name, psw, sex, phone, qq, photo) values(#{stuno},#{name},#{psw},#{sex},#{phone},#{qq},#{photo})")
    void insertStudent(Student student);

    @Select("insert into teacher(teachno,name,gra_class,course,psw,sex,phone,qq,photo)\n" +
            "        values(#{teachno},#{name},#{gra_class},#{course},#{psw},#{sex},#{phone},#{qq},#{photo})")
    void insertTeacher(Teacher teacher);

    @Update("UPDATE stu SET name = #{name},sex = #{sex},psw = #{psw},phone = #{phone},qq = #{qq},photo = #{photo} WHERE stuno = #{stuno}")
    void updateStudent(Student student);

    @Update("UPDATE teacher SET name = #{name},sex = #{sex},gra_class = #{gra_class},course = #{course},qq = #{qq},phone = #{phone},photo = #{photo} WHERE teachno = #{teachno}")
    void updateTeacher(Teacher teacher);

    @Select("select * from stu where stuno=#{num}")
    List<Student> findStudentById(@Param("num") String id);

    @Select("select * from teacher where teachno=#{num}")
    List<Teacher> findTeacherById(@Param("num") String id);

    @Delete("delete from stu where stuno=#{num}")
    int deleteStudentById(@Param("num") String id);

    @Delete("delete from teacher where teachno=#{num}")
    int deleteTeacherById(@Param("num") String id);

    int batchDeleteTeachersByNums(String[] teachNos);

}
