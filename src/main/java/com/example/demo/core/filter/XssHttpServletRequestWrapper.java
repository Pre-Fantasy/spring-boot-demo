package com.example.demo.core.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author  Pre_fantasy
 * @create  2018-06-29 19:45
 * @desc    重写请求参数处理函数，这是实现XSS过滤的关键，在其内重写了getParameter，getParameterValues，getHeader等方法，
 *          对http请求内的参数进行了过滤。package com.example.demo.core.filter;
 **/
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    HttpServletRequest orgRequest = null;

    private boolean isInclludeRichText = false;

    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isInclludeRichText) {
        super(request);
        orgRequest = request;
        this.isInclludeRichText = isInclludeRichText;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/29 19:58
     *  @param  {String name}
     *  @return String
     *  @desc   覆盖getParameter方法，将参数名和参数值都做xss过滤
     *          如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     *          getParameterNames, getParameterValue和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        if (("content".equals(name) || name.endsWith("WithHtml")) && !isInclludeRichText) {
            return super.getParameter(name);
        }
        name = XssFilterUtil.clean(name);
        String value = super.getParameter(name);
        if (StringUtils.isNoneBlank(value)) {
            value = XssFilterUtil.clean(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = XssFilterUtil.clean(arr[i]);
            }
        }
        return arr;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/29 20:10
     *  @param  {String name}
     *  @return String
     *  @desc   覆盖getHeader方法，将参数名和参数值都都做Xss过滤
     *          如果需要获得原始的值，则通过super.getHeaders(name)来获取
     *          getHeaderNames 也可能需要覆盖
     */
    public String getHeader(String name) {
        name = XssFilterUtil.clean(name);
        String value = super.getHeader(name);
        if (StringUtils.isNoneBlank(value)) {
            value = XssFilterUtil.clean(value);
        }
        return value;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/29 20:15
     *  @param
     *  @return HttpServletRequest
     *  @desc   获取最原始的request
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/6/29 20:18
     *  @param  {HttpSerletRequest req}
     *  @return HttpServletRequest
     *  @desc   获取最原始的request的静态方法
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }
        return req;
    }

}
