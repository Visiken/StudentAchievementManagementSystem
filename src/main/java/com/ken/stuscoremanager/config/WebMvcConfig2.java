//package com.ken.stuscoremanager.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * @author ken
// * @version 1.0.0
// * @date 2023/5/10 12:30
// * @description 配置类
// */
//@Slf4j
//@Configuration
////@EnableSwagger2
////public class WebMvcConfig extends WebMvcConfigurationSupport {
//public class WebMvcConfig{
//
//    /**
//     * 以下内容为swagger需要的
//     * @return
//     */
//    @Bean
//    public Docket createRestApi() {
//        //文档类型
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.ken.stuscoremanager.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    /**
//     * 一下内容为swagger中展示的项目信息
//     * @return
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("学生成绩管理系统")
//                .version("1.0.0")
//                .description("学生管理系统文档接口")
//                .build();
//    }
//}
