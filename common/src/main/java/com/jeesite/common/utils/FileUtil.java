package com.jeesite.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public class FileUtil {

    private static String dz = "/www/server/tomcat/webapps";

    public static String downloadFile(String urlString,String filePath) {
        boolean bool = false;
        String fileType = ".jpg";
        InputStream is = null;
        FileOutputStream os = null;
        String sss= null;
        try {
            // 构造URL
            java.net.URL url = new java.net.URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            // 输入流
            is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            //判断指定目录是否存在，不存在则先创建目录


            String[] urlname = urlString.split("/");
            int len1 = urlname.length-1;
            //获取文件名
            String uname = urlname[len1];
            File file = new File(filePath);
            if (!file.exists())
                file.mkdirs();
            //fileName如果不包含文件后缀，则需要加上后缀，如：fileName + ".jpg";fileName + ".txt";
            sss = filePath + uname+ fileType;
            os = new FileOutputStream(sss, false);//false：覆盖文件,true:在原有文件后追加
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

            bool = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 完毕，关闭所有链接
            if (null != os){
                try {
                    os.flush();
                    os.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String fileurl ="https://file.twcreaotr.com" + sss.replace(dz,"");
        return fileurl;
    }
}
