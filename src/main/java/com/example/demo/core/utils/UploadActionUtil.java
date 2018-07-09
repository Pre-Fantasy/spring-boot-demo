package com.example.demo.core.utils;


import com.example.demo.core.constant.ProjectConstant;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dell
 * @create 2018-07-06 16:46
 * @desc 创建文件上传工具
 **/
public class UploadActionUtil {

    public static List<String> uploadFile(HttpServletRequest request) throws Exception {
        List<String> list = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiReuqest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = multiReuqest.getFileNames();
            while (iterator.hasNext()) {
                /*获取上传文件*/
                MultipartFile file = multiReuqest.getFile(iterator.next());
                if (file != null) {
                    /*获取当前文件的文件名称*/
                    String myFileName = file.getOriginalFilename();
                    /*如果名称不是空字符串“”，说明文件存在，否则说明文件不存在*/
                    if (myFileName != null && ! "".equals(myFileName.trim())) {
                        String fileTypes = myFileName.substring(myFileName.lastIndexOf("."));
                        String tempName = UUID.randomUUID().toString() + fileTypes;
                        /*创建文件夹*/
                        String folderPath = ProjectConstant.SAVEFILEPATH + File.separator + folderName();
                        File fileFolder = new File(folderPath);
                        if (!fileFolder.exists() && !fileFolder.isDirectory()) {
                            fileFolder.mkdir();
                        }
                        File fileUpload = new File(folderPath + File.separator + tempName);
                        file.transferTo(fileUpload);
                        myFileName = folderName() + File.separator + tempName;
                        list.add(ProjectConstant.SAVEFILEPATH + "//" + myFileName);
                    }
                }

            }
        }
        return list;
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/8 14:16
     *  @param
     *  @return String 年月日文件夹名称
     *  @desc   获取年月日文件夹名称
     */
    public static String getCurrentFileName() throws Exception {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + "" + (now.get(Calendar.MONTH) + 1) + "" + now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/8 14:23
     *  @param  {fileName(要创建的文件夹的名称<String>)}
     *  @return null
     *  @desc   创建文件夹
     */
    public static void createFilder(String fileName)throws Exception {
        File file = new File(fileName);
        /*判断文件夹是否存在，不存在则创建文件夹*/
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
    }

    /**
     *  @author Pre_fantasy
     *  @create 2018/7/8 14:27
     *  @param  {fileName(文件全名<String>}
     *  @return String 文件后缀名
     *  @desc   获取文件扩展名称方法
     */
    public static String extFile(String fileName) throws Exception {
        return fileName.substring(fileName.lastIndexOf("."));
    }


    /**
     *  @author Pre_fantasy
     *  @create 2018/7/8 13:55
     *  @param
     *  @return String 文件名
     *  @desc   通过日期获取文件名称
     */
    private static String folderName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(new Date());
        return str;
    }
}
