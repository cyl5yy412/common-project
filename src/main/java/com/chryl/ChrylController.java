package com.chryl;

import com.chryl.util.FileUtil;
import com.chryl.util.PrimaryKeyFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by Chryl on 2020/1/27.
 */
@Controller
@RequestMapping("/chryl")
public class ChrylController {

    public static final String uploadPath = "/Users/chryl/upload/common/";
    // 允许上传的img格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};
    private static final String[] DOC_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};

    //上传
    @PostMapping("/upload")
    public void uploadAll2doc(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            //prefix:自主生成文件名
            String prefix = PrimaryKeyFactory.generatePK("");
            //上传时的文件名字
            String originalFilename = file.getOriginalFilename();
            //文件格式
            String suffix = StringUtils.substringAfterLast(originalFilename, ".");
            if (StringUtils.equalsAnyIgnoreCase(suffix, "bmp", "jpg", "jpeg", "gif", "png")) {//img
                // 校验图片格式
                boolean isLegal = false;
                for (String type : IMAGE_TYPE) {
                    if (StringUtils.endsWithIgnoreCase(originalFilename, type)) {
                        isLegal = true;
                        break;
                    }
                }
            } else if (StringUtils.equalsAnyIgnoreCase(suffix, "doc", "docx", "txt", "wps")) {//doc
                boolean isLegal = false;

                for (String type : DOC_TYPE) {
                    if (StringUtils.endsWithIgnoreCase(originalFilename, type)) {
                        isLegal = true;
                        break;
                    }
                }
            }
            String fileName = prefix + "." + suffix;
//            boolean flag = FileUtil.upLoadFile(file, uploadPath, fileName);
//            System.out.println(flag);
            boolean flag2 = FileUtil.upLoadFileByDate(file, uploadPath, fileName);
            System.out.println(flag2);
        }


    }

    //下载
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {

        boolean b = FileUtil.downloadFile(uploadPath, fileName, "", response);
    }
    //展示

    @GetMapping("/showimg")
    public void showimg(@RequestParam(required = false) String path, HttpServletResponse response) {
        if (path == null || path.trim().length() == 0) {
            path = "/Users/chryl/upload/common/20200127/13881196847010329.jpg";
        }
        File file = new File(path);
        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()
        ) {
            if (file.exists()) {

                byte[] bytes = new byte[inputStream.available()];
                int length = 0;
                while ((length = inputStream.read(bytes)) != -1) {
                    System.out.println("length in ::" + length);
                    //写出客户端
                    outputStream.write(bytes, 0, length);
                }
                System.out.println("length::" + length);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/showimg2")
    public void showimg2(String path, HttpServletResponse response) {
        //从本地读取文件并返回到网页中
        FileInputStream in = null;
        ServletOutputStream out = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            out = response.getOutputStream();
            byte[] bytes = new byte[1024 * 10];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("showimg3")
    public void show4(String path, HttpServletResponse response) throws IOException {

        InputStream inputStream = null;
        try (
                OutputStream outputStream = response.getOutputStream()
        ) {
            File file = new File(path);
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            System.out.println("inputStream.available()::" + inputStream.available());
            inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
    }

    @RequestMapping(value = "/showimg4", method = RequestMethod.GET)
    public void showimg4(HttpServletResponse response, String path) throws Exception {
        try {
            String filePath = "根路径" + path;
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                int i = fis.available(); // 得到文件大小   
                byte data[] = new byte[i];
                fis.read(data); // 读数据
                response.setContentType("image/*"); // 设置返回的文件类型   
                OutputStream toClient = response.getOutputStream();// 得到向客户端输出二进制数据的对象   
                toClient.write(data);// 输出数据
                toClient.flush();
                toClient.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("图片不存在");
        }
    }

    //删除
    @DeleteMapping("/delete/{fileName}")
    public void deleteFile(@PathVariable String fileName) {
//        String path = uploadPath + fileName;
        String path = uploadPath;
        File file = new File(path);
        if (file.exists()) {
            FileUtil.deleteFile(file, fileName);
        }
    }

    //    测试展示和下载
    @GetMapping("/show/img")
    public void showImgcyl(HttpServletResponse response) throws UnsupportedEncodingException {
        //fileName 带有后缀
        String fileName = "13881201370310319.jpg";
        String path = uploadPath + "13881201370310319.jpg";

        response.reset(); // 非常重要
        //下载以福建的形式传递到客户端
        response.addHeader("Content-Disposition", //
                "attachment;fileName=" +
                        new String(fileName.getBytes("utf-8"), "iso8859-1"));// 设置文件名
        File file = new File(path);
        try (
                InputStream inputStream = new FileInputStream(file);
                OutputStream outputStream = response.getOutputStream();
        ) {

            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            outputStream.write(data);
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
