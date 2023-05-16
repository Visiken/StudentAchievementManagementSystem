package com.ken.stuscoremanager.service.imp;

import com.ken.stuscoremanager.dao.StudentScoreMapper;
import com.ken.stuscoremanager.entity.StudentScore;
import com.ken.stuscoremanager.service.StudentScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/13 15:24
 * @description 学生分数实现类
 */
@Service
public class StudentScoreServiceImpl implements StudentScoreService {

    @Resource
    private StudentScoreMapper studentScoreMapper;


    @Override
    public List<StudentScore> getScoreByName(String name) {
        return studentScoreMapper.getScoreByName(name);
    }

    @Override
    public List<StudentScore> findScore(String grade, String cla, String coursename, String type, Integer offset, Integer limit) {
        int page = (offset - 1)*limit;
        return studentScoreMapper.findScore(grade, cla, coursename, type, page, limit);
    }

    @Override
    public List<StudentScore> scoreCount(String grade, String cla, String courseName, String type) {
        return studentScoreMapper.scoreCount(grade, cla, courseName, type);
    }

    @Override
    public List<StudentScore> findGcsCount(String coursename, String grade, String cla, String type) {
        return studentScoreMapper.findGcsCount(coursename, grade, cla, type);
    }

    @Override
    public List<StudentScore> getGscomp(String coursename, String grade) {
        return studentScoreMapper.getGscomp(coursename, grade);
    }


}
