package com.ken.stuscoremanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ken
 * @version 1.0.0
 * @date 2023/5/11 13:54
 * @description 配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * addViewControllers 方法是 WebMvcConfigurer 接口中定义的一个默认方法，它允许通过添加自定义的 ViewController，来实现将请求映射到指定的视图上。
     * addViewControllers 方法是被重写的，并且使用了 registry 参数来注册一个空白的 ViewController，以便在请求映射中将其与特定的 URL 路径进行匹配。
     * 需要注意的是，该方法并非必须重写，如果没有特殊的需求可以忽略该方法。
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        WebMvcConfigurer.super.addViewControllers(registry);
//      当用户在浏览器中访问 "/ajaxdemo" 路径时，Spring MVC 框架将会自动将该请求与视图名称 "ajaxdemo" 相匹配，并返回给用户对应的视图页面。
//        在浏览器器中访问这里的url路径可以访问到对应的静态资源
        registry.addViewController("/ajaxdemo").setViewName("/ajaxdemo");
        registry.addViewController("/layuidemo").setViewName("/layuidemo");
        registry.addViewController("/laginPage").setViewName("/login");
        registry.addViewController("/welcome").setViewName("/welcome");

        // 三个不同的用户的登录页面
        registry.addViewController("/stuIndex").setViewName("/stuIndex");
        registry.addViewController("/teacherIndex").setViewName("/teacherIndex");
        registry.addViewController("/adminIndex").setViewName("/adminIndex");
        // 学生登录页面的一些信息
        registry.addViewController("/stuInfo_mf").setViewName("/stuInfo_mf");
        registry.addViewController("/stuScore").setViewName("/stuScore");
        //教师登录页面的一些信息
        registry.addViewController("/teacherInfo_mf").setViewName("/teacherInfo_mf");
        registry.addViewController("/claCouScore").setViewName("/claCouScore");
        registry.addViewController("/parseClaCouSco").setViewName("/parseClaCouSco");
        registry.addViewController("/parseClaComp").setViewName("/parseClaComp");
        //管理员页面的信息
        registry.addViewController("/stuInfo").setViewName("/stuInfo");
        registry.addViewController("/stuAdd").setViewName("/stuAdd");
        registry.addViewController("/teacherInfo").setViewName("/teacherInfo");
        registry.addViewController("/teaAdd").setViewName("/teaAdd");
        //这只做页面跳转不做参数传递，所以一下需要在controller中写一个方法用来接收传递的参数并映射到视图中
        registry.addViewController("/stuModi").setViewName("/stuModi");
        registry.addViewController("/teaModi").setViewName("/teaModi");
    }
}
