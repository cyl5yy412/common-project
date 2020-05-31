package com.chryl.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * file上传下载工具类
 * <p>
 * Created By Chr on 2019/6/24.
 */
@Slf4j
public class FileUtil {

    /**
     * 展示图片
     *
     * @param path 图片路径
     */
    public static void showImg(String path, HttpServletResponse response) {

        File file = new File(path);
        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()
        ) {
            if (file.exists()) {

                byte[] bytes = new byte[inputStream.available()];
                int length = 0;
                while ((length = inputStream.read(bytes)) != -1) {
                    System.out.println("length in ::" + length);
                    //写出客户端:展示直接写出到客户端,不需要以附件的方式
                    outputStream.write(bytes, 0, length);
                }
                System.out.println("length::" + length);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单个文件上传:原文件名上传
     *
     * @param file 前端的file
     * @param path 存储本地的path    /Chryl
     * @return
     */
    public static boolean upLoadFile(MultipartFile file, String path, String fileName) {
        if (file.isEmpty()) {
            return false;
        }
//        int size = (int) file.getSize();
        File dest = new File(path + "/" + fileName);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("上传失败--------");
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("上传失败--------");
            return false;
        }
    }

    /**
     * 单个文件上传:日期文件夹/原文件名上传
     *
     * @param file     前端的file
     * @param path     存储本地的path /Chryl/
     * @param fileName 传入的文件名字
     * @return
     */
    public static boolean upLoadFileByDate(MultipartFile file, String path, String fileName) {
        if (file.isEmpty()) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        String savePath = path;
        savePath += ymd + "/";

        File dirFile = new File(savePath);
//        if (!dirFile.getParentFile().exists()) {
//            dirFile.getParentFile().mkdir();
//        }
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File dest = new File(savePath + fileName);
        System.out.println(dest.getParentFile().getName());
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("上传失败--------");
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("上传失败--------");
            return false;
        }
    }

    /**
     * 下载
     *
     * @param path        路径
     * @param fileNewName 上传时自定义name,有后缀名
     * @param fileOldName 用户上传时的文件名字,有后缀名
     * @param res
     * @return
     * @throws UnsupportedEncodingException
     */
    public static boolean downloadFile(String path, String fileNewName, String fileOldName, HttpServletResponse res) throws IOException {
        if (StringUtils.isBlank(fileNewName)) {
            return false;
        }
        //通过文件名查找文件信息fileInfo=selectByFileName(fileName);
        //查看文件信息,存储路径
        res.setContentType("application/force-download");// 设置强制下载不打开
        res.setHeader("Context-Type", "application/xmsdownload");
        //4.告诉浏览器不要打开文件，直接下载，原因是IE6会直接打开文件，所以这个算是特意为IE6设置的
//        res.setContentType("application/x-msdownload");

        //无弹窗,直接下载
//        res.setContentType("application/x-download");
//        res.setContentType("application/application/octet-stream");
        //.*（ 二进制流，不知道下载文件类型）
//        res.setContentType("application/multipart/form-data");
        //下载只是以附件的方式传递到客户端
        res.addHeader("Content-Disposition", //
                "attachment;fileName=" +
                        new String(fileNewName.getBytes("utf-8"), "iso8859-1"));// 设置文件名
        //判断文件是否存在
        File file = new File(Paths.get(path, fileNewName).toString());

        if (file.exists()) {
            try (
                    InputStream iss = new FileInputStream(file);
                    OutputStream oss = res.getOutputStream()//可输出字符和字节
            ) {
                int length;
//                byte[] data = new byte[1024 << 3];
                byte[] data = new byte[iss.available()];
                while ((length = iss.read(data)) != -1) {
                    oss.write(data, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*
        if (file.exists()) {
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = res.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                log.debug("下载成功:{}", fileOldName);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }*/
        //

        return true;
    }


    /**
     * 删除文件
     *
     * @param f        文件所在的目录
     * @param fileName 需要删除的文件名
     * @return
     */
    public static void deleteFile(File f, String fileName) {
        //数组指向文件夹中的文件和文件夹
        File[] fi = f.listFiles();
        //遍历文件和文件夹
        for (File file : fi) {
            //如果是文件夹，递归查找
            if (file.isDirectory())
                deleteFile(file, fileName);
            else if (file.isFile()) {
                //是文件的话，把文件名放到一个字符串中
                String filename = file.getName();
                //如果是“class”后缀文件，返回一个boolean型的值
                /*if (filename.endsWith("class")) {
                    System.out.println("成功删除：：" + file.getName());
                    //file.delete();
                }*/
                if (fileName.equals(filename)) {
                    System.out.println("成功删除：：" + file.getName());
                    file.delete();
                }
            }
        }

    }

    public static void main(String args[]) {
//        File file = new File("/Users");  //  /Users是路径名
//        File file = new File("D:/upload");  //  "D:/upload"  Users是路径名
//        delete(file, "java.txt");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        System.out.println(ymd);
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        String savePath = "/chryl/";
        savePath += ymd + "/";
        System.out.println(savePath);
    }
}
