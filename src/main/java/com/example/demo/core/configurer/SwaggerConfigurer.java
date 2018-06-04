package com.example.demo.core.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @author Pre_fantasy
 * @create 2018-06-01 18:13
 * @desc   Swagger是一款通过我们添加的注解来对方法进行说明，来自动生成项目的在线api接口文档的web服务。
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfigurer {

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/1 18:37
     *  @param  ""      null
     *  @return Docket
     *  @desc   不理解这里能干嘛？
     */
    @Bean
    public Docket createRestApi () {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("java.com.example.demo.controller"))
                .paths(PathSelectors.any())
                .build();


    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/1 18:39
     *  @param  ""      null
     *  @return APiInfo
     *  @desc   不理解这里能干嘛？
     */
    private ApiInfo apiInfo () {
        return new ApiInfoBuilder()
                .title("mySpringBoot 使用Swagger2构建RESTful APIs")
                .description("更多Spring Boot相关文章请关注：https://juejin.im/user/59e7fb9451882578e1406a51/posts")
                .termsOfServiceUrl("https://juejin.im/user/59e7fb9451882578e1406a51/posts")
                .contact(new Contact("Pre_fantasy", "https://gitee.com/beany/mySpringBoot", null))
                .version("1.0")
                .build();
    }



}
