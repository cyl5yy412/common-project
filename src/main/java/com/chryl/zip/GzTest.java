package com.chryl.zip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Chr.yl on 2020/8/30.
 *
 * @author Chr.yl
 */
public class GzTest {
    public static void main(String[] args) {
        String filePath = "/Users/chryl/Desktop/切换硬盘格式22.png";

        show(filePath);

    }


    public static String show(String fileName) {
        String outFileName = fileName + ".gz";

        try (
                //建立gz压缩文件流
                FileInputStream fileInputStream = new FileInputStream(fileName);
                // out
                FileOutputStream fileOutputStream = new FileOutputStream(outFileName);
                //gzip
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);

        ) {

            int len;
            byte[] buf = new byte[fileInputStream.available()];
            while ((len = fileInputStream.read(buf, 0, buf.length)) != -1) {
                gzipOutputStream.write(buf, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFileName;
    }
}