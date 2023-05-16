package com.ken.stuscoremanager.service.imp;

import com.ken.stuscoremanager.dao.AdminMapper;
import com.ken.stuscoremanager.entity.Admin;
import com.ken.stuscoremanager.entity.Student;
import com.ken.stuscoremanager.entity.Teacher;
import com.ken.stuscoremanager.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/12 13:34
 * @description 管理服务层的接口实现
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin adminCheckExist(String username, String password) {
        return adminMapper.adminCheckExist(username, password);
    }

    @Override
    public Student insertStudent(Student student) {
        adminMapper.insertStudent(student);
        return null;
    }

    @Override
    public Teacher insertTeacher(Teacher teacher) {
        adminMapper.insertTeacher(teacher);
        return null;
    }

    @Override
    public Student updateStudent(Student student) {
        adminMapper.updateStudent(student);
        return null;
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        adminMapper.updateTeacher(teacher);
        return null;
    }

    @Override
    public List<Student> findStudentById(String id) {
        return adminMapper.findStudentById(id);
    }

    @Override
    public List<Teacher> findTeacherById(String id) {
        return adminMapper.findTeacherById(id);
    }

    @Override
    public int deleteStudentById(String id) {
        return adminMapper.deleteStudentById(id);
    }

    @Override
    public int deleteTeacherById(String id) {
        return adminMapper.deleteTeacherById(id);
    }


}
