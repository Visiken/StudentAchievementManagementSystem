package com.ken.stuscoremanager.service;

import com.ken.stuscoremanager.entity.StudentScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/13 15:23
 * @description 学生分数
 */
public interface StudentScoreService {

    List<StudentScore> getScoreByName(String name);

    List<StudentScore> findScore(String grade, String cla, String coursename, String type, Integer offset, Integer limit);

    List<StudentScore> scoreCount(String grade, String cla, String courseName, String type);

    List<StudentScore> findGcsCount(String coursename, String grade, String cla, String type);

    List<StudentScore> getGscomp(String coursename, String grade);

}
