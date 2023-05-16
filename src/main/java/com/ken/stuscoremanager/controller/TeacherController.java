package com.ken.stuscoremanager.controller;

import com.alibaba.fastjson.JSON;
import com.ken.stuscoremanager.entity.StudentScore;
import com.ken.stuscoremanager.entity.Teacher;
import com.ken.stuscoremanager.service.StudentScoreService;
import com.ken.stuscoremanager.service.TeacherService;
import com.ken.stuscoremanager.utils.CountTool;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/12 13:29
 * @description 教师web层的管理功能
 * RestController的功能如下：
 * ResponseBody+Controller=RestController
 */
@RestController
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @Resource
    private StudentScoreService studentScoreService;

    /**
     * 分页
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getTeaSimpleInfo")
    public String getTeaSimpleInfo(String page,String limit){
        int page_ = Integer.parseInt(page);
        int limit_ = Integer.parseInt(limit);
        List<Teacher> teachersByPage = teacherService.findTeachersByPage(page_, limit_);
        int count = teacherService.teacherCount();
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", count);
        map.put("data", teachersByPage);
        return JSON.toJSONString(map);
    }

    /**
     * 搜索
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getTeaByName")
    public String getTeaByName(@Param("key[id]") String name, String page, String limit){
        int page_ = Integer.parseInt(page);
        int limit_ = Integer.parseInt(limit);
        List<Teacher> teachersByNames = teacherService.findTeachersByNames(name, page_, limit_);
        int count = teacherService.teacherCount();
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", count);
        map.put("data", teachersByNames);
        return JSON.toJSONString(map);
    }

    /**
     *
     * @param page
     * @param limit
     * @param grade
     * @param cla
     * @param coursename
     * @param type
     * @return
     */
    @RequestMapping("/getGcs")
    public String getGcs(@RequestParam("key[grade]") String grade,
                         @RequestParam("key[cla]") String cla,
                         @RequestParam("key[coursename]") String coursename,
                         @RequestParam("key[type]") String type,
                         String page,String limit){
        int page_ = Integer.parseInt(page);
        int limit_ = Integer.parseInt(limit);
        List<StudentScore> score = studentScoreService.findScore(grade, cla, coursename, type, page_, limit_);
        List<StudentScore> count = studentScoreService.scoreCount(grade, cla, coursename, type);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", count);
        map.put("data", score);
        return JSON.toJSONString(map);
    }


    /**
     * 对比统计
     * @param coursename
     * @param grade
     * @param cla
     * @param type
     * @return
     */
    @RequestMapping("/getGcsCount")
    public String getGcsCount(String coursename, String grade, String cla, String type){
        List<StudentScore> gcsCount = studentScoreService.findGcsCount(coursename, grade, cla, type);
        List<Map> mapList = new ArrayList<>();
        Map map1 = new HashMap();
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        Map map4 = new HashMap();
        Map map5 = new HashMap();
        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);
        mapList.add(map4);
        mapList.add(map5);
        // [{优:5},{中:}]
        int num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0;
        for (StudentScore score : gcsCount) {
            Float sco = score.getScore();
            if (sco >= 90){
                num1++;
            } else if (sco >= 80 && sco < 90){
                num2++;
            } else if (sco >= 70 && sco < 80){
                num3++;
            } else if (sco >= 60 && sco < 70){
                num4++;
            } else if (sco < 60){
                num5++;
            }
        }
        // 处理方法scoreList，以符号前端饼状图的数据结构
        if (num1 != 0){
            map1.put("quality", "优");
            map1.put("count", num1);
        }
        if (num2 != 0){
            map2.put("quality", "良");
            map2.put("count", num2);
        }
        if (num3 != 0){
            map3.put("quality", "一般");
            map3.put("count", num3);
        }
        if (num4 != 0){
            map4.put("quality", "较差");
            map4.put("count", num4);
        }
        if (num5 != 0){
            map5.put("quality", "较差");
            map5.put("count", num5);
        }
        return JSON.toJSONString(mapList);
    }

    @RequestMapping("/getGscomp")
    public String getGscomp(String coursename,String grade){
        List<StudentScore> scoreList = studentScoreService.getGscomp(coursename, grade);
        List<Float> scoreList1 = new ArrayList<>();
        List<Float> scoreList2 = new ArrayList<>();
        List<Float> scoreList3 = new ArrayList<>();

        float sum1 = 0f;
        float sum2 = 0f;
        float sum3 = 0f;

        for (StudentScore score : scoreList) {
            //根据班级划分分数01 02 03，使用字符串分割substring方法获取学号中的两位，再使用equals比较字符串是否相等
            if (score.getStuno().substring(4,6).equals("01")){
                sum1 += score.getScore();
                scoreList1.add(score.getScore());
            } else if (score.getStuno().substring(4,6).equals("02")){
                sum2 += score.getScore();
                scoreList2.add(score.getScore());
            } else if (score.getStuno().substring(4,6).equals("03")){
                sum3 += score.getScore();
                scoreList3.add(score.getScore());
            }
        }

        //这里使用了一个工具类，该工具类已经写好了求平均值，最大值、最小值，这里直接调用即可
        float avg1 = CountTool.aveNums(sum1, scoreList1.size());
        float maxScore1 = CountTool.maxNum(scoreList1);
        float midScore1 = CountTool.midnum(scoreList1);

        float avg2 = CountTool.aveNums(sum2, scoreList2.size());
        float maxScore2 = CountTool.maxNum(scoreList2);
        float midScore2 = CountTool.midnum(scoreList2);

        float avg3 = CountTool.aveNums(sum3, scoreList3.size());
        float maxScore3 = CountTool.maxNum(scoreList3);
        float midScore3 = CountTool.midnum(scoreList3);

        float[][] data = new float[3][3];
        data[0][0] = avg1;
        data[0][1] = maxScore1;
        data[0][2] = midScore1;

        data[1][0] = avg2;
        data[1][1] = maxScore2;
        data[1][2] = midScore2;

        data[2][0] = avg3;
        data[2][1] = maxScore3;
        data[2][2] = midScore3;
        return JSON.toJSONString(data);
    }
}
