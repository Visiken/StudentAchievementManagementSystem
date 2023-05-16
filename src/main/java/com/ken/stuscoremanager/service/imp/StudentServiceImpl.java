package com.ken.stuscoremanager.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ken.stuscoremanager.dao.StudentMapper;
import com.ken.stuscoremanager.entity.Student;
import com.ken.stuscoremanager.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/10 22:16
 * @description 实现类
 */
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    /**
     * 手动分页
     * @param offset 起始位置是从0开始的
     * @param limit 每页的条数
     * @return
     */
    @Override
    public List<Student> findStudentPage(Integer offset, Integer limit) {
        // 计算起始位置
        int page = (offset - 1) * limit;
        return studentMapper.findStudentPage(page, limit);
    }

    @Override
    public Integer studentCount() {
        return studentMapper.studentCount();
    }

    @Override
    public Student studentCheckExist(String stuno, String password) {
        return studentMapper.studentCheckExist(stuno, password);
    }

    /**
     * 使用分页助手分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<Student> findByPageHelper(int pageNum, int pageSize) {
        // 计算起始位置
        int page = (pageNum - 1) * pageSize;

        try {
            // 分页参数设置
            PageHelper.startPage(page, pageSize);

        } finally {
            // 避免线程池复用导致分页数据一致存在
            PageHelper.clearPage();
        }
        return studentMapper.findByPageHelper(page, pageSize);
    }

    /**
     * 模糊查询学生
     * @param name
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<Student> findByStuName(String name, int offset, int limit) {
        //计算起始位置
        int page = (offset - 1) * limit;
        return studentMapper.findByStuName(name, page, limit);
    }

}
