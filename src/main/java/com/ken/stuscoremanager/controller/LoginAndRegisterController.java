package com.ken.stuscoremanager.controller;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.ken.stuscoremanager.dao.AdminMapper;
import com.ken.stuscoremanager.dao.StudentMapper;
import com.ken.stuscoremanager.dao.TeacherMapper;
import com.ken.stuscoremanager.entity.Admin;
import com.ken.stuscoremanager.entity.Student;
import com.ken.stuscoremanager.entity.Teacher;
import com.ken.stuscoremanager.service.AdminService;
import com.ken.stuscoremanager.service.StudentService;
import com.ken.stuscoremanager.service.TeacherService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/11 11:34
 * @description 用户登录
 */
@Controller
public class LoginAndRegisterController {

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private AdminService adminService;

    /**
     * 去浏览器查看前端请求数据是否与后端数据相匹配
     * @param username
     * @param password
     * @param identify
     * @param session 临时会话存储 httpSession
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestParam(value = "num") String username, @RequestParam(value = "psw") String password, String identify, HttpSession session){
        // 确定登录者的身份
        // 转换成包装类
        int identityCode = Integer.parseInt(identify);

        //根据用户名和密码完成登录
        // 管理员
        String dataJson = "fail";
        if (identityCode == 0) {
            Admin admin = adminService.adminCheckExist(username, SecureUtil.md5(password));
            if (admin != null) {
                //用户存在, 放入Session
                session.setAttribute("account", admin.getAccount());
                session.setAttribute("name", admin.getName());
                session.setAttribute("photo", "admin.png");
                session.setAttribute("role", "admin");
                dataJson = JSON.toJSONString(admin);
            }

        // 老师
        }else if (identityCode == 1) {
            Teacher teacher = teacherService.teacherCheckExist(username, SecureUtil.md5(password));
            if (teacher != null) {
                session.setAttribute("account", teacher.getTeachno());
                session.setAttribute("name", teacher.getName());
                session.setAttribute("photo", teacher.getPhoto());
                session.setAttribute("role", "teacher");
                dataJson = JSON.toJSONString(teacher);
            }


        // 学生
        }else if (identityCode == 2) {
            //这里使用工具类中的MD5加密方式SecureUtil 安全工具 secure 安全
            Student student = studentService.studentCheckExist(username, SecureUtil.md5(password));
            //学信息不为空则返回数据，为空则返回fail
            if (student != null){
                session.setAttribute("account", student.getStuno());
                session.setAttribute("name", student.getName());
                session.setAttribute("photo", student.getPhoto());
                session.setAttribute("role", "stu");
                dataJson = JSON.toJSONString(student);
            }
        }
        return dataJson;
    }


    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.invalidate();
        return "login";
    }
}
