package com.ken.stuscoremanager.controller;

import cn.hutool.db.PageResult;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ken.stuscoremanager.entity.Student;
import com.ken.stuscoremanager.entity.StudentScore;
import com.ken.stuscoremanager.service.StudentScoreService;
import com.ken.stuscoremanager.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/12 13:28
 * @description 学生web层的管理功能
 */
@Slf4j
@Controller
public class StudentController {

    @Resource
    private StudentService studentService;

    @Resource
    private StudentScoreService studentScoreService;

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return 这里同时有post和get请求
     */
    @ResponseBody
    @PostMapping("/getStuSimpleInfo")
    public String getStuSimpleInfo(@RequestParam("page") String page,@RequestParam("limit") String limit){
        int page_ = Integer.parseInt(page);
        int limit_ = Integer.parseInt(limit);
        // 输出一下日志
        log.info("page"+ page_);
        log.info("limit"+limit_);

        //使用pagehelper分页助手
        Page<Student> studentPage = studentService.findByPageHelper(page_, limit_);

        //手动分页的方式
//        List<Student> studentPage = studentService.findStudentPage(page_, limit_);mk
        Integer count = studentService.studentCount();

        // code, msg, count, data
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("code", 0);
        mapData.put("msg", "");
        mapData.put("count", count);
        mapData.put("data", studentPage);
        // 转换json串返回
        return JSON.toJSONString(mapData);
    }

    /**
     * 模糊查询
     * @param name
     * @param page
     * @param limit
     * @return 这里的请求参数与浏览器中传递的参数一致
     */
    @ResponseBody
    @PostMapping("/getStuByName")
    public String getStuByName(@RequestParam("key[id]") String name,@RequestParam("page") String page,@RequestParam("limit") String limit){
        int page_ = Integer.parseInt(page);
        int limit_ = Integer.parseInt(limit);
        Integer count;
        List<Student> byStuName;

        Map<String, Object> mapData = new HashMap<>();
        //如果查询的名字为空则只返回分页，否则进行模糊查询后并分页返回
        if ("".equals(name)){
            byStuName = studentService.findStudentPage(page_, limit_);
        }else {
            byStuName = studentService.findByStuName(name, page_, limit_);
        }
        count = studentService.studentCount();
        mapData.put("code", 0);
        mapData.put("msg", "");
        mapData.put("count", count);
        mapData.put("data", byStuName);
        return JSON.toJSONString(mapData);
    }


    /**
     * 查询学生成绩
     * 获取登录时存储会话的信息
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/getScoreByStuName")
    public String getScoreByStuName(HttpSession session){
        String name = (String) session.getAttribute("name");
        List<StudentScore> scoreByName = studentScoreService.getScoreByName(name);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", scoreByName.size());
        map.put("data", scoreByName);
        return JSON.toJSONString(map);
    }



}
