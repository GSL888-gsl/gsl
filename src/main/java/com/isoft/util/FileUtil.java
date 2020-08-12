package com.isoft.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUtil {
    /**
     * 保存上传文件
     */
    public static boolean saveUpFile(HttpServletRequest request, String path, String newFileName, MultipartFile file){
        boolean result=false;
// 路径检测
        ServletContext application=request.getServletContext();
        String absPath=application.getRealPath("/"+path);
        File f = new File(absPath);
        if (!f.exists())   {
            f.mkdirs();
        }

        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(f+File.separator+newFileName);
            InputStream fis=file.getInputStream();
            byte[] buffer = new byte[1024 * 8] ;
            int len  ;
            while ((len = fis.read(buffer, 0, buffer.length)) != -1) {
                fos.write(buffer , 0 , len);
            }
            fos.flush();
            result=true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!=fos){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos=null;
            }
        }
        return result;
    }


    /**
     * 文件下载
     */
    public static void downFile(HttpServletRequest request , HttpServletResponse response , String path , String filename) throws IOException {
        //2.使用字节输入流加载文件到内存
        //2.1找到文件服务器路径
        ServletContext servletContext = request.getServletContext();
        String realPath = servletContext.getRealPath("/" + path + "/" + filename);
        //2.1用字节流关联
        FileInputStream fis = new FileInputStream(realPath);

        //解决中文名乱码问题
        //获取浏览器请求头
        String userAgent = request.getHeader("user-agent");
        //处理编码格式
        filename = getFileName(userAgent, filename);

        //3.设置response的响应头
        //3.1设置响应头类型：content-type
        String mimeType = servletContext.getMimeType(filename);
        response.setHeader("content-type",mimeType);
        //3.2设置响应头打开方式：content-disposition
        response.setHeader("content-disposition","attachment;filename="+filename);

        //4.将输入流的数据写出到输出流中
        ServletOutputStream sos = response.getOutputStream();
        byte[] bytes=new byte[1024*8];
        int len;
        while ((len=fis.read(bytes))!=-1){
            sos.write(bytes,0,len);
        }
    }
    // 上传配置
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 4; // 4MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 10; // 10MB
    public static final String KEY_FILE = "upfile" ;

    /**
     *  文件上传
     * @param request
     * @param response
     * @param path  上传文件所保存路径
     * @return  Map中包含有用户上传的普通文件域信息，key名同表单元素名 + key为upfile的用户上传后保存文件的绝对url
     */

    public static Map<String , String> fileFormData(HttpServletRequest request , HttpServletResponse response , String path) {
        String fileName = null ;
        Map<String , String> map = new HashMap<>() ;
        // 获取文件上传的绝对物理路径
        String upPath = request.getServletContext().getRealPath("/") + File.separator + path ;
//        System.out.println(upPath);
        // 如果目录不存在，则创建目录
        File uploadDirectory = new File(upPath) ;
        if(! uploadDirectory.exists()) {
            uploadDirectory.mkdirs() ;
        }
        // 文件上传配置
        DiskFileItemFactory factory = new DiskFileItemFactory() ;
        ServletFileUpload upload = new ServletFileUpload(factory) ;
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
        upload.setHeaderEncoding("UTF-8");

        // 上传内容解析
        try {
            // 获取所有表单提交数据：文件内容+普通文本
            List<FileItem> formItems = upload.parseRequest(request) ;
            if(null != formItems && formItems.size() > 0) {
                // 迭代处理表单数据
                for(FileItem item : formItems) {
                    if(item.isFormField()) {  // 普通表单元素
                        map.put(item.getFieldName() , item.getString("UTF-8")) ;
                    } else {  // 文件域
                        String oriName = item.getName() ;
                        String extName = oriName.substring(oriName.lastIndexOf(".")) ;
                        fileName = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + extName ;
                        String fileFullName = upPath + File.separator + fileName ;
                        item.write(new File(fileFullName));
                        map.put(KEY_FILE , url(request , path , fileName)) ;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map ;
    }

    /**
     * 将参数路径+文件名字映射一个网络绝对url : http:// ....
     * @param path
     * @param fileName
     * @return
     */
    public static String url(HttpServletRequest request , String path , String fileName) {
        path = path == null ? "" : path ;
        String protocol = request.getProtocol() ;  // HTTP/1.1
        protocol = protocol.substring(0 , protocol.indexOf("/")) ;
        String url = protocol + "://" +
                request.getServerName() + ":" +
                request.getServerPort() + "/" +
                request.getContextPath() + "/" +
                path + "/" + fileName ;
        System.out.println(url);
        return url;
    }

    /**
     * 改变文件名编码格式，避免中文乱码
     */
    public static String getFileName(String agent, String filename) throws UnsupportedEncodingException {
        if(null == agent) {
            return URLEncoder.encode(filename, "utf-8") ;
        }
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = "=?utf-8?B?" + Base64.getEncoder().encode(filename.getBytes("utf-8")) + "?=";
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
