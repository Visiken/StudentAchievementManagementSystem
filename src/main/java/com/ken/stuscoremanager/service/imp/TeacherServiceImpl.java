package com.ken.stuscoremanager.service.imp;

import com.ken.stuscoremanager.dao.TeacherMapper;
import com.ken.stuscoremanager.entity.Teacher;
import com.ken.stuscoremanager.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/12 13:33
 * @description 教师服务层的接口实现
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public Teacher teacherCheckExist(String username, String password) {
        return teacherMapper.teacherCheckExist(username, password);
    }

    @Override
    public List<Teacher> findTeachersByPage(Integer offset, Integer limit) {
        //计算起始位置
        int page = (offset - 1) * limit;
        return teacherMapper.findTeachersByPage(page, limit);
    }

    @Override
    public Integer teacherCount() {
        return teacherMapper.teacherCount();
    }

    @Override
    public List<Teacher> findTeachersByNames(String name, Integer offset, Integer limit) {
        int page = (offset - 1) * limit;
        return teacherMapper.findTeachersByNames(name, page, limit);
    }
}
