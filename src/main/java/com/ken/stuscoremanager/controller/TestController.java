package com.ken.stuscoremanager.controller;

import com.alibaba.fastjson.JSON;
import com.ken.stuscoremanager.dao.StudentMapper;
import com.ken.stuscoremanager.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/10 10:56
 * @description web层
 * //@Controller 把方法的返回值对应到视图名称
 * //@RestController  把方法的返回值作为ResponseBody 返回json字符，直接返回给浏览器 Controller + ResponseBody
 */
//@RestController
@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private StudentMapper studentMapper;

    @RequestMapping("/index")
    public String demo() {
        return "index";
    }

   @GetMapping("/ajaxGet")
   @ResponseBody
   public String ajaxGet(String name,String password){
       System.out.println("---ajaxGet---");
       System.out.println("name: " + name);
       System.out.println("password: " + password);
       return "success";
   }

    @ResponseBody
    @PostMapping("/ajaxPost")
    public String ajaxPost(String name,String password){
        System.out.println("---ajaxPost---");
        System.out.println("name: " + name);
        System.out.println("password: " + password);
        return "success";
    }

    @ResponseBody
    @GetMapping("/ajax")
    public String ajax(String name,String password){
        System.out.println("---ajax---");
        System.out.println("name: " + name);
        System.out.println("password: " + password);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/testjson")
    public String testJson(){
        Student student = new Student();
        student.setName("刘备");
        student.setId(1);
        student.setPhone("https://localhost:8080");
        student.setPhone("123123123");
        student.setQq("12312313");

//        将对象转换成json对象
        String s = JSON.toJSONString(student);
//        控制台输出一下结果
        System.out.println(s);
        return s;
    }

    /**
     * 浏览器输入的数据要与此处一致，否则会报错
     * @param startPage
     * @param pageSize
     * @return
     * ?startPage=1&pageSize=5
     * @RequestParam(value = "page") Integer startPage,@RequestParam(value = "limit") Integer pageSize
     * 参数绑定
     */
    @ResponseBody
    @GetMapping("/page")
    public String pageSize(@RequestParam(value = "page") Integer startPage,@RequestParam(value = "limit") Integer pageSize) {
        System.out.println("startPage: " + startPage);
        System.out.println("pageSize: " + pageSize);
        List<Student> studentPage = studentMapper.findStudentPage(startPage, pageSize);
//        使用键值对的形式的字符串输出
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg","success!!");
        map.put("count",100);
        map.put("data", studentPage);
//      将对象转成json字符串
        return JSON.toJSONString(map);
//        return "layuiDemo.html";
    }
}
