package com.cy.homework.controller;


import com.cy.homework.common.Result;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("file")
public class UploadFileHandler {
    /**上传地址*/
    @Value("${file.upload.file.path}")
    private String path;

    /**图片url前缀*/
    @Value("${file.upload.file.base_file_url}")
    private String base_file_url;

    /**
     * 上传图片
     * @param file
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/upload")
    public Result<?> uploadPicture(@RequestPart("file") MultipartFile file,
                                   @RequestParam("filename")String fileName) throws IOException {

        //获取文件在服务器的储存位置
        File filePath = new File(path);
        System.out.println("文件的保存路径"+path);
        if(!filePath.exists() && !filePath.isDirectory()){
            System.out.println("目录不存在，创建目录" + filePath);
            filePath.mkdir();
        }

        //获取原始文件名称（包括格式）
        String originalFileName = file.getOriginalFilename();
        System.out.println("原文件名称" + originalFileName);

        //获取文件类型，以最后一个‘.’为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型" + type);

        System.out.println("新文件名称" +fileName);

        //在指定路径下创建文件
        File targetFile = new File(path,fileName);

        Map<String,Object> result = new HashMap<String,Object>();//定义结果
        //将文件保存到服务器指定位置
        try{
            file.transferTo(targetFile);
            System.out.println("上传成功");
        }catch (IOException e){
            System.out.println("上传失败");
            result.put("code",400);
            e.printStackTrace();
            return Result.OK(result);
        }

        result.put("code",200);
        result.put("picture",base_file_url+ fileName);
        System.out.println(result);
        return Result.OK(result);
    }

    @ResponseBody
    @PostMapping("/upload/student")
    public Result<?> uploadFileStudent(@RequestPart("file") MultipartFile file,
                                   @RequestParam("filename")String fileName) throws IOException {

        //获取文件在服务器的储存位置
        String fp = "E:/Project/bishe/homework/upload/file/student";

        File filePath = new File(fp);
        System.out.println("文件的保存路径"+fp);
        if(!filePath.exists() && !filePath.isDirectory()){
            System.out.println("目录不存在，创建目录" + filePath);
            filePath.mkdir();
        }

        //获取原始文件名称（包括格式）
        String originalFileName = file.getOriginalFilename();
        System.out.println("原文件名称" + originalFileName);

        //获取文件类型，以最后一个‘.’为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型" + type);

        System.out.println("新文件名称" +fileName);

        //在指定路径下创建文件
        File targetFile = new File(fp,fileName);

        Map<String,Object> result = new HashMap<String,Object>();//定义结果
        //将文件保存到服务器指定位置
        try{
            file.transferTo(targetFile);
            System.out.println("上传成功");
        }catch (IOException e){
            System.out.println("上传失败");
            result.put("code",400);
            e.printStackTrace();
            return Result.OK(result);
        }

        result.put("code",200);
        result.put("picture",base_file_url+ fileName);
        System.out.println(result);
        return Result.OK(result);
    }

    @GetMapping("/downloadTFile")
    public Object downloadFile(HttpServletResponse res, @RequestParam("fileName") String fileName) throws IOException {
        //文件名 能够经过形参传进来
        //要下载的文件地址 能够经过形参传进来
        String filepath = path + fileName;

        OutputStream os = null;//输出文件流
        InputStream is = null;//输入文件流
        try {
            // 取得输出流
            os = res.getOutputStream();
            // 清空输出流
            res.reset();
            res.setContentType("application/x-download;charset=GBK");//设置响应头为文件流
            res.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("utf-8"), "iso-8859-1"));//设置文件名
            // 读取流
            File f = new File(filepath);
            is = new FileInputStream(f);
            if (is == null) {
                System.out.println("下载附件失败");
            }
            // 复制
            IOUtils.copy(is, res.getOutputStream());//经过IOUtils的copy函数直接将输入文件流的内容复制到输出文件流内
            res.getOutputStream().flush();//刷新输出流
        } catch (IOException e) {
            System.out.println("下载附件失败");
        }
        // 文件的关闭放在finally中
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                System.out.println("输入流关闭异常");
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                System.out.println("输出流关闭异常");
            }
        }
        return null;
    }


    @GetMapping("/downloadSFile")
    public Object downloadSFile(HttpServletResponse res, @RequestParam("fileName") String fileName) throws IOException {
        //文件名 能够经过形参传进来
        //要下载的文件地址 能够经过形参传进来
        String filepath = path +"student/"+ fileName;

        OutputStream os = null;//输出文件流
        InputStream is = null;//输入文件流
        try {
            // 取得输出流
            os = res.getOutputStream();
            // 清空输出流
            res.reset();
            res.setContentType("application/x-download;charset=GBK");//设置响应头为文件流
            res.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("utf-8"), "iso-8859-1"));//设置文件名
            // 读取流
            File f = new File(filepath);
            is = new FileInputStream(f);
            if (is == null) {
                System.out.println("下载附件失败");
            }
            // 复制
            IOUtils.copy(is, res.getOutputStream());//经过IOUtils的copy函数直接将输入文件流的内容复制到输出文件流内
            res.getOutputStream().flush();//刷新输出流
        } catch (IOException e) {
            System.out.println("下载附件失败");
        }
        // 文件的关闭放在finally中
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                System.out.println("输入流关闭异常");
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                System.out.println("输出流关闭异常");
            }
        }
        return null;
    }

}
