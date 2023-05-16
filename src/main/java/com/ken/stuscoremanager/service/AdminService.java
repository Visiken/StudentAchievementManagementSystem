package com.ken.stuscoremanager.service;

import com.ken.stuscoremanager.entity.Admin;
import com.ken.stuscoremanager.entity.Student;
import com.ken.stuscoremanager.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/12 13:34
 * @description 管理员的服务
 */
@Transactional(rollbackFor = Exception.class)
public interface AdminService {

    Admin adminCheckExist(String username, String password);

    Student insertStudent(Student student);

    Teacher insertTeacher(Teacher teacher);

    Student updateStudent(Student student);

    Teacher updateTeacher(Teacher teacher);

    List<Student> findStudentById(String id);

    List<Teacher> findTeacherById(String id);

    int deleteStudentById(String id);

    int deleteTeacherById(String id);

}
