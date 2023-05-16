package com.ken.stuscoremanager.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.ken.stuscoremanager.entity.Student;
import com.ken.stuscoremanager.entity.Teacher;
import com.ken.stuscoremanager.service.AdminService;
import com.ken.stuscoremanager.service.StudentScoreService;
import com.ken.stuscoremanager.service.StudentService;
import com.ken.stuscoremanager.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/12 13:31
 * @description 管理员的web层操作
 */
@Slf4j
@Controller
public class AdminController {

    @Resource
    private StudentScoreService studentScoreService;

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private AdminService adminService;


    /**
     * 学生信息分页查询
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping("/getStuInfo")
    public String getStuInfo(String page, String limit){
        int page_ = Integer.parseInt(page);
        int limit_ = Integer.parseInt(limit);
        List<Student> studentPage = studentService.findStudentPage(page_, limit_);
        Integer count = studentService.studentCount();
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", count);
        data.put("data", studentPage);
        return JSON.toJSONString(data);
    }


    /**
     * 教师信息分页查询
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTeaInfo")
    public String getTeaInfo(String page, String limit){
        int page_ = Integer.parseInt(page);
        int limit_ = Integer.parseInt(limit);
        List<Teacher> teachersByPage = teacherService.findTeachersByPage(page_, limit_);
        Integer count = teacherService.teacherCount();
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", count);
        data.put("data", teachersByPage);
        return JSON.toJSONString(data);
    }

    /**
     * 使用注解@Value给属性注入属性值，（属性值是.yml文件中自定义的上传和下载路径）
     */
    @Value("{management.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     * 浏览器中传递过来的参数为photo，这里进行了参数匹配
     */
    @ResponseBody
    @RequestMapping("/uploadImg")
    public String uploadImg(@RequestParam("photo") MultipartFile file){
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());
        //原始文件名 如 abc.png
        String originalFilename = file.getOriginalFilename();
        //截取文件的后缀名，
        // subString方法：只有一个参数时，从指定位置开始一直截取到字符串末尾结束
        // 有两个参数时，从指定位置开始截取到结束位置（不包含结束位置的索引下标）截取的内容为起始位置和两个位置之间的字符
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String newFileName = UUID.randomUUID().toString() + suffix;
        //创建一个目录对象
        File dir = new File(basePath);
        //判断当前文件是否存在
        if (!dir.exists()) {
            //不存在则创建文件夹，注意:mkdirs()创建由这个抽象路径名命名的目录,包括任何必要的但不存在的父目录。
            dir.mkdirs();
        }

        // 将临时文件转存到目录下
        try {
            //这个是将原来的文件保存到具体的目录下，现在修改了源文件的名字，所以需要重新创建一个新的文件用来存储已经修改后的文件
//            file.transferTo(dir);demo
            file.transferTo(new File(basePath + newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //截取新的文件名不包含扩展名,如果需要返回文件名则需要截取新的文件名返回
        String newFileName2 = newFileName.substring(0,newFileName.lastIndexOf("."));

        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "success");
        data.put("data", newFileName2);
        return JSON.toJSONString(data);
    }


    /**
     * 添加学生信息
     * @return
     * @RequestBody 返回json字符串
     */
    @ResponseBody
    @RequestMapping("/registerStuDeal")
    public String registerStuDeal(@RequestBody Student student){
        //打印一下添加学生的日志信息
        log.info(student.toString());
        //get() 用于从 Map 集合中按照指定键获取对应的值（value）
        String psw = student.getPsw();
        student.setPsw(SecureUtil.md5(psw));
        //插入数据的异常处理，插入成功返回成功，插入失败返回失败
        try {
            adminService.insertStudent(student);
        } catch (NullPointerException e) { // 空指针异常
            log.error("操作失败：发生空指针异常！", e);
            return "fail: " + e.getMessage();
        } catch (DataAccessException e) { // 数据库访问异常
            log.error("操作失败：数据库访问异常！", e);
            return "fail: " + e.getMessage();
        } catch (Exception e) { // 其他未知异常
            log.error("操作失败：发生未知异常！", e);
            return "fail: " + e.getMessage();
        }
        return "success";
    }


    /**
     * 添加老师信息
     * @return
     * get() 用于从 Map 集合中按照指定键获取对应的值（value）
     * //        Object psw = map.get("psw");
     * //        map.put("psw", SecureUtil.md5(psw.toString()));
     *
     * 注意：使用map传递参数不安全
     */
    @ResponseBody
    @RequestMapping("/registerTeaDeal")
    public String registerTeaDeal(@RequestBody Teacher teacher){
        //打印一下添加学生的日志信息
        log.info(teacher.toString());
        String psw = teacher.getPsw();
        teacher.setPsw(SecureUtil.md5(psw));
        //插入数据的异常处理，插入成功返回成功，插入失败返回失败
        try {
            adminService.insertTeacher(teacher);
        } catch (NullPointerException e) { // 空指针异常
            log.error("操作失败：发生空指针异常！", e);
            return "fail: " + e.getMessage();
        } catch (DataAccessException e) { // 数据库访问异常
            log.error("操作失败：数据库访问异常！", e);
            return "fail: " + e.getMessage();
        } catch (Exception e) { // 其他未知异常
            log.error("操作失败：发生未知异常！", e);
            return "fail: " + e.getMessage();
        }
        return "success";
    }


    /**
     * 修改学生信息
     * @param student
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateStu")
    public String updateStu(Student student){
        //打印一下日志
        log.info("准备更新中....");
        try {
            adminService.updateStudent(student);
        } catch (NullPointerException e) { // 空指针异常
            log.error("操作失败：发生空指针异常！", e);
            return "fail: " + e.getMessage();
        } catch (DataAccessException e) { // 数据库访问异常
            log.error("操作失败：数据库访问异常！", e);
            return "fail: " + e.getMessage();
        } catch (Exception e) { // 其他未知异常
            log.error("操作失败：发生未知异常！", e);
            return "fail: " + e.getMessage();
        }
        return "";
    }


    /**
     * 修改教师信息
     * @param teacher
     * @return
     */
   @ResponseBody
   @RequestMapping("/updateTea")
   public String updateTea(Teacher teacher){
       //打印一下日志
       log.info("准备更新中....");
       try {
           adminService.updateTeacher(teacher);
       } catch (NullPointerException e) { // 空指针异常
           log.error("操作失败：发生空指针异常！", e);
           return "fail: " + e.getMessage();
       } catch (DataAccessException e) { // 数据库访问异常
           log.error("操作失败：数据库访问异常！", e);
           return "fail: " + e.getMessage();
       } catch (Exception e) { // 其他未知异常
           log.error("操作失败：发生未知异常！", e);
           return "fail: " + e.getMessage();
       }
       return "";
   }

    /**
     * 修改学生信息接收传递数据的视图
     * @param mav
     * @param num
     * @return
     */
    @RequestMapping("/stuModi")
    public ModelAndView stuModi(ModelAndView mav, @RequestParam("num") String num) {
        mav.addObject("num", num);
        mav.setViewName("stuModi");
        return mav;
    }

    /**
     * 修改教师信息接收传递数据的视图
     * @param num
     * @return
     */
    @RequestMapping("/teaModi")
    public ModelAndView teaModi(String num){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("teaModi");
        mav.addObject("num", num);
        return mav;
    }

    /**
     * 根据学号获取学生信息
     * @param num
     * @return
     */
   @ResponseBody
   @RequestMapping("/getStuByNum")
   public String getStuByNum(String num){
       log.info("学号是："+num);
       List<Student> studentList = adminService.findStudentById(num);
       Map<String, Object> data = new HashMap<>();
       data.put("code", 0);
       data.put("msg", "");
       data.put("count", studentList.size());
       data.put("data", studentList);
       return JSON.toJSONString(data);
   }


    /**
     * 根据教师编号获取教师信息
     * @param num
     * @return
     */
   @ResponseBody
   @RequestMapping("/getTeaByNum")
   public String getTeaByNum(String num){
       log.info("教师编号是："+num);
       List<Teacher> teacherList = adminService.findTeacherById(num);
       Map<String, Object> data = new HashMap<>();
       data.put("code", 0);
       data.put("msg", "");
       data.put("count", teacherList.size());
       data.put("data", teacherList);
       return JSON.toJSONString(data);
   }


    /**
     * 批量删除学生信息
     * @param nums
     * @return
     */
//   @ResponseBody
//   @RequestMapping("/deleteStus")
//   public String deleteStus(String nums){
//       return "";
//   }


    /**
     * 根据id删除学生
     * @return
     */
   @ResponseBody
   @RequestMapping("/deleteStu")
   public String deleteStu(String num){
       int result = adminService.deleteStudentById(num);
       if (result > 0){
           return "success";
       }
       return "";
   }


    /**
     * 批量删除老师信息
     * @param nums
     * @return
     */
//   @ResponseBody
//   @RequestMapping("/delAll")
//   public String delAll(String nums){
//       return "";
//   }


    /**
     * 根据id删除老师信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteTeacher")
    public String deleteTeacher(String num){
        int result = adminService.deleteTeacherById(num);
        if (result > 0) {
            return "success";
        }
        return "";
    }
}
