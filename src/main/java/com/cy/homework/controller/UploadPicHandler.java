package com.cy.homework.controller;


import com.cy.homework.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pic")
public class UploadPicHandler {
    /**上传地址*/
    @Value("${file.upload.pic.path}")
    private String path;

    /**图片url前缀*/
    @Value("${file.upload.pic.base_picture_url}")
    private String base_picture_url;

    /**
     * 上传图片
     * @param file
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/upload")
    public Result<?> uploadPicture(@RequestPart("file") MultipartFile file,
                                   @RequestParam("picname")String picname) throws IOException {

        //String base_picture_url = "http://localhost:8080/image/";

        //获取文件在服务器的储存位置
        File filePath = new File(path);
        System.out.println("文件的保存路径"+path);
        if(!filePath.exists() && !filePath.isDirectory()){
            System.out.println("目录不存在，创建目录" + filePath);
            filePath.mkdir();
        }

        //获取原始文件名称（包括格式）
        String originalFileName = file.getOriginalFilename();
        System.out.println("原始文件名称" + originalFileName);

        //获取文件类型，以最后一个‘.’为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型" + type);

        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0,originalFileName.lastIndexOf("."));

        //设置文件新名称：当前事件+文件名称（不包含格式）
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String date = sdf.format(d);
        String fileName = picname + "pic." +type;
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
        result.put("picture",base_picture_url+ fileName);
        System.out.println(result);
        return Result.OK(result);
    }
}
