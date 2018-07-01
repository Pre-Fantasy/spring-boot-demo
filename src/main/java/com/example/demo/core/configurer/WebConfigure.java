package com.example.demo.core.configurer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.example.demo.core.ret.RetCode;
import com.example.demo.core.ret.RetResult;
import com.example.demo.core.ret.ServiceException;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebConfigure extends WebMvcConfigurationSupport {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebConfigure.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/META-INF/resources/favicon.ico");
        super.addResourceHandlers(registry);
    }

    @Override
    public void  configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
        converter.setSupportedMediaTypes(getSupportedMediaTypes());
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                /*String null -> ""*/
                SerializerFeature.WriteNullStringAsEmpty,
                /*Number null -> 0*/
                SerializerFeature.WriteNullNumberAsZero,
                //禁止循环引用
                SerializerFeature.DisableCircularReferenceDetect
        );
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter);

    }
    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);

        return supportedMediaTypes;
    }

    /*
     *  config.setSerializerFeatures()
     *  WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
     *  WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
     *  DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
     *  WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
     *  WriteMapNullValue：是否输出值为null的字段,默认为false
    */


    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(getHandlerExceptionResolvers());
    }


   /**
     *  @author Pre_fantasy
     *  @create 2018/5/29 22:43
     *  @param
     *  @return HandlerExceptionResolver
     *  @desc   创建异常处理
     */
    private HandlerExceptionResolver getHandlerExceptionResolvers() {

        HandlerExceptionResolver handlerExceptionResolver = new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                                 Object handler, Exception e) {
                RetResult<Object> result = getResuleByHeandleException(request, handler, e);

                responseResult(response, result);
                return new ModelAndView();

            }
        };
        return handlerExceptionResolver;
    }


    /**
     *  @author Pre_fantasy
     *  @create 2018/5/29 23:34
     *  @param  request     HttpServletRequest
     *  @param  handler     Object
     *  @param  e           Exception
     *  @return RetResult</Object>
     *  @desc   根据异常类型确定返回数据
     */
    private RetResult<Object> getResuleByHeandleException (HttpServletRequest request, Object handler, Exception e) {

        RetResult result = new RetResult();
        if (e instanceof ServiceException) {
            result.setCode(RetCode.FALT).setMsg(e.getMessage()).setData(null);
            LOGGER.info(e.getMessage());
            return result;
        }

        if (e instanceof NoHandlerFoundException) {
            result.setCode(RetCode.NOT_FOUND).setMsg("接口 [ " + request.getRequestURI() + " ] 不存在");
            return result;
        }
        if (e instanceof UnauthorizedException) {
            result.setCode(RetCode.UNAUTHZ).setMsg("用户没有访问权限！").setData(null);
            return result;
        }
        if (e instanceof  UnauthenticatedException) {
            result.setCode(RetCode.UNAUTHEN).setMsg("用户未登陆！").setData(null);
            return result;
        }

        result.setCode(RetCode.INTERNAL_SERVER_ERROR).setMsg("接口 [ " + request.getRequestURI() + " ] 内部错误，请联系管理员" );
        String message;

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                    request.getRequestURI(), handlerMethod.getBean().getClass().getName(), handlerMethod.getMethod() .getName(), e.getMessage());
        } else {
            message = e.getMessage();
        }

        LOGGER.error(message, e);
        return result;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/5/31 22:13
     *  @param  response
     *  @param  result
     *  @return void
     *  @desc   响应结果
     */
    private void responseResult(HttpServletResponse response, RetResult<Object> result) {

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {

            response.getWriter().write(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }
}
