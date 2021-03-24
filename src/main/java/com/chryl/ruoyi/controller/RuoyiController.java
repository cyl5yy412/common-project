package com.chryl.ruoyi.controller;

import com.chryl.ruoyi.utils.FileUtils;
import com.chryl.ruoyi.utils.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.tools.ant.types.resources.MultiRootFileSet.SetType.file;

/**
 * Created by Chr.yl on 2021/3/24.
 *
 * @author Chr.yl
 */
@RestController
@RequestMapping("ruoy")
public class RuoyiController {

//    @GetMapping("upload")
//    public void show(MultipartFile file)) {
//        // 上传文件路径
//        String filePath = RuoYiConfig.getUploadPath();
//        // 上传并返回新文件名称
//        String fileName = FileUploadUtils.upload(filePath, file);
//    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 本地资源路径
//        String localPath = "";
        // 数据库资源地址--substringAfter截取指定目录后的目录
//        String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
        // 下载名称--substringAfterLast取/之后最后一个
//        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");

        //------------------------

        String downloadName = "wfile.txt";
        String downloadPath = "/Users/chryl/upload/wfile.txt";

        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }

}
