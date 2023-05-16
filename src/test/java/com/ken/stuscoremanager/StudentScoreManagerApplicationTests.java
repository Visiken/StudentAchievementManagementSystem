package com.ken.stuscoremanager;

import com.ken.stuscoremanager.dao.StudentMapper;
import com.ken.stuscoremanager.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class StudentScoreManagerApplicationTests {
    @Resource
    private StudentMapper studentMapper;

    @Test
    void contextLoads() {
        List<Student> studentPage = studentMapper.findStudentPage(1, 5);
        System.out.println(studentPage);
    }
}
