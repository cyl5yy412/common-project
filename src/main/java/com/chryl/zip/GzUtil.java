package com.chryl.zip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Chr.yl on 2020/11/15.
 *
 * @author Chr.yl
 */
public class GzUtil {
    public static void main(String[] args) {
        String fileName = "/Users/chryl/Desktop/切换硬盘格式22.png";

        compressFile(fileName);
    }


    public static String compressFile(String fileName) {
        String outFileName = fileName + ".gz";

        try (
                //建立gz压缩文件流
                FileInputStream in = new FileInputStream(fileName);
                // out
                FileOutputStream fileOutputStream = new FileOutputStream(outFileName);
                //gzip
                GZIPOutputStream out = new GZIPOutputStream(fileOutputStream);

        ) {

            int len = 0;
            byte[] buf = new byte[10240];

            while ((in.available() > 10240) && (in.read(buf)) > 0) {
                out.write(buf);
            }
            len = in.available();
            in.read(buf, 0, len);
            out.write(buf, 0, len);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFileName;

    }
}
