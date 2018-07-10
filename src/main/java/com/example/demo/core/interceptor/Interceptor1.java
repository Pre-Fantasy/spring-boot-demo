package com.example.demo.core.interceptor;

import com.example.demo.core.systemlog.SystemLogQueue;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pre_fantasy
 * @create 2018-07-10 11:53
 * @desc 个人定义拦截器
 **/
public class Interceptor1 implements HandlerInterceptor {


    /**
     *  @author Pre_fantasy
     *  @create 2018/7/10 14:05
     *  @param  {request<HttpServletRequest>, response<HttpServletResponse>, handler<Object>}
     *  @return boolean
     *  @desc   在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        System.out.println(">>>MyInterceptor1>>>>>>>> preHandler");
        /*只返回true才会继续向下执行，返回false取消当前请求*/
        return true;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/10 14:28
     *  @param  {request<HttpServletRequest>, response<HttpServletReqponse>,
     *          handler<Object>, modelAndView<ModelAndView>}
     *  @return
     *  @desc   请求处理之后进行调用，但是在视图渲染之前，（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                            Object handler, ModelAndView modelAndView) {
        System.out.println(">>>MyInterceptor1>>>>>>>> postHandler");
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/10 14:20
     *  @param  {request<HttpServletRequest>, response<HttpServletResponse>, handler<Object>, ex<Exception>}
     *  @return 
     *  @desc   在整个请求接受之后会被调用， 也就是在DispatcherServlet渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception{
        System.out.println(">>>MyInterceptor1>>>>>>> afterCompletion");
    }
}
