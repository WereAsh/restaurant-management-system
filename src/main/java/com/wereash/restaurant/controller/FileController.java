package com.wereash.restaurant.controller;

import com.wereash.restaurant.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/*
* 文件上传与下载
* */
@Slf4j
@RestController
@RequestMapping("/common")
public class FileController {

    @Value("${image.uploadPath}")
    private String uploadBasePath;
    /*
    * 此处的参数名必须与前端发送请求中的name一致
    * */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //file是一个临时文件，需要在转存到指定位置，否则本次请求完成后的临时文件会删除
        log.info(file.toString());
        //原始文件名
        String originalFilename=file.getOriginalFilename();
        //使用UUID重新生成文件名。防止文件名称重复造成文件覆盖
        String s= UUID.randomUUID().toString();
        String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName=s+suffix;

        //创建一个目录对象,判断是否存在，不存在则创建目录
        File dir=new File(uploadBasePath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(uploadBasePath+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){

        try {
            //输出流，通过输入流读取文件内容
            FileInputStream fileInputStream=new FileInputStream(new File(uploadBasePath+name));
            //输出流，通过输出流将文件写回浏览器，在浏览器展示图片了
            ServletOutputStream  outputStream=response.getOutputStream();

            response.setContentType("image/jpg");

            int len=0;
            byte[] bytes=new byte[1024];
            while((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
