package com.example.demo.core.configurer;

import com.example.demo.core.shiro.CustomRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author  Pre_fantasy
 * @create  2018-06-30 19:22
 * @desc    添加shiro配置
 **/
@Configuration
public class ShiroConfigurer {

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/30 19:24
     *  @param
     *  @return Realm
     *  @desc   注入自定义的realm，告诉shiro如何获取用户信息来做登录或者权限控制
     */
    @Bean
    public Realm realm() {
        return new CustomRealm();
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /*setUsePrefix(true)用以解决一个特殊bug，在引入aop的情况下，在@Controller注解类的方法中加入@RequiresRoles注解，会导致该方法无法映射请求，出现404，计入这个配置就能解决此问题*/
        creator.setUsePrefix(true);
        return creator;
    }
    
    /**
     *  @author Pre_fantasy
     *  @create 2018/6/30 20:47
     *  @param
     *  @return ShiroFilterChainDefinition
     *  @desc   这里统一做鉴权，即判断那些请求需要用户登录，那些请求不需要用户登录
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/userInfo/selectById", "authc, roles[admin]");
        chainDefinition.addPathDefinition("/logout", "anon");
        chainDefinition.addPathDefinition("/userInfo/selectAll", "anon");
        chainDefinition.addPathDefinition("/userInfo/login", "anon");
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }


}
