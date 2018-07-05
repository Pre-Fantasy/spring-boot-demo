package com.example.demo.core.aop;

import com.alibaba.fastjson.JSON;
import com.example.demo.core.systemlog.SystemLogQueue;
import com.example.demo.core.ret.ServiceException;
import com.example.demo.core.utils.ApplicationUtils;
import com.example.demo.model.SystemLog;
import com.example.demo.model.UserInfo;
import com.example.demo.service.SystemLogService;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  Pre_fantasy
 * @create  2018-07-04 20:15
 * @desc    创建切面
 **/
@Aspect
@Component
public class AspectLog {

    private static final Logger logger = LoggerFactory.getLogger(AspectLog.class);

    @Resource
    private SystemLogQueue systemLogQueue;

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/4 20:31
     *  @param  {null}
     *  @return void
     *  @desc   定义切点
     */
    @Pointcut("@annotation(com.example.demo.core.aop.AnnotationLog)")
    public void methodCachePointcut() {

    }
    
    @Before("methodCachePointcut()")
    public void deBefore(JoinPoint p) throws Exception {
        SystemLog systemLog = getSystemLogInit(p);
        systemLog.setLogType(SystemLog.LOGINFO);
        systemLogQueue.add(systemLog);
    }

    private SystemLog getSystemLogInit(JoinPoint p) {
        SystemLog systemLog = new SystemLog();
        try {

            /*类名*/
            String targetCalss = p.getTarget().getClass().getName();

            /*请求的方法名称*/
            String tartgetMethod = p.getSignature().getName();

            /*获取类名称 例如  UserInfoController*/
            String classType = p.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();

            /*请求参数+参数值的map*/
            Map<String, Object> nameAndArgs = getFieldName(this.getClass(), clazzName, tartgetMethod, p.getArgs());
            systemLog.setId(ApplicationUtils.getUUID());
            systemLog.setDescription(getMethodRemark(p));
            systemLog.setMethod(targetCalss + "." + tartgetMethod);
            /*获取ip的方法; String IP = getIp()*/
            systemLog.setRequestIp("192.168.1.104");
            systemLog.setParams(JSON.toJSONString(nameAndArgs));
            systemLog.setUserId(getUserId());
            systemLog.setCreateTime(new Date());
            
        } catch (Exception e) {
            logger.error("==异常通知==");
            logger.error("异常信息：{}", e.getMessage());
        }
        return systemLog;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/5 11:41
     *  @param
     *  @return String
     *  @desc   获取用户编号
     */
    private static String getUserId() {
        String userId = "";
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (userInfo != null) {
            userId = userInfo.getId();
        }
        return userId;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/5 11:25
     *  @param  {joinPoint[JoinPoint]}
     *  @return String
     *  @desc   获取方法中的备注-- 用于记录用户的操作日志描述
     */
    private static  String getMethodRemark(JoinPoint joinPoint) throws  Exception{
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String method = "";
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    AnnotationLog methodCache = m.getAnnotation(AnnotationLog.class);
                    if (methodCache != null) {
                        method = methodCache.remark();
                    }
                    break;
                }
            }
        }
        return method;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/4 20:46
     *  @param  {clz[Class], clazzName[String], methodName[String], args[Object[]] }
     *  @return Map<String, Object>
     *  @desc   通过反射机制 获取被切参数以及参数值
     */
    private Map<String,Object> getFieldName(Class clz, String clazzName, String methodName,
                                            Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<>();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classpath = new ClassClassPath(clz);
        pool.insertClassPath(classpath);
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            /*HttpServletRequest 和 HttpServletResponse 不做处理*/
            if (!(args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletRequest)) {
                //paramName即为参数名
                map.put(attr.variableName(i + pos), JSON.toJSONString(args[i]));
            }
        }
        return map;

    }
}
