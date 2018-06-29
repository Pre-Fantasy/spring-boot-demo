package com.example.demo.core.filter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.lang.annotation.Documented;

/**
 * @author  dell
 * @create  2018-06-29 19:27
 * @desc    xss非法过滤标签
 *          XSS攻击全称跨站脚本攻击，是一种在web应用中的计算机安全漏洞，它允许恶意web用户将代码植入到提供给其它用户使用的页面中。
 *          1. 在任何一个表单内，你输入一段简单的js代码：<script>for(var i=0;i<1000;i++){alert("弹死你"+i);}</script>，
 *          将其存入数据库； 2. 在页面上一个div元素内直接展示第一步内存入的值，你会发现弹出框出现了；以上两个示例仅仅算是恶作剧，
 *          恶意用户能做的更多，如获取用户信息，进行“网络钓鱼”攻击等。应对XSS攻击的其中一个方式就是后端对输入内容进行过滤，
 *          输入内容里面的敏感信息直接过滤，如<script>标签等。
 **/
public class XssFilterUtil {

    /**
     * 使用自带的basicWithImages 白名单
     * 允许的便签有a,b,blockquote，br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,strike,strong,sub,sup,u,ul,img
     * 以及a标签的href， img标签的 src， aling,alt,height,width,title 属性
     */
    private static final Whitelist whitelist = Whitelist.basicWithImages();

    /*配置过滤化参数，不对代码进行格式化*/
    private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

    static {
        /**
         * 富文本编辑时一些样式是使用style来进行实现的
         * 比如红字体style="color:red;"
         * 所以需要给所有标签添加style属性
         */
        whitelist.addAttributes(":all", "style");
    }

    public static String clean(String content) {
        return Jsoup.clean(content, "", whitelist, outputSettings);
    }

}
