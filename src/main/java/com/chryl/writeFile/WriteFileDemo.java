package com.chryl.writeFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 写入文件
 * Created by Chr.yl on 2020/11/24.
 *
 * @author Chr.yl
 */
public class WriteFileDemo {


    // 1
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();

        stringList.add("123456" + "\n");
        stringList.add("abcdefg" + "\n");

        String totLine = "";
        for (String line : stringList) {
            totLine = totLine + line;
        }

        File wfile = new File("/Users/chryl/upload/wfile.txt");
        try (
                BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(wfile, true),
                                "utf-8"
                        )
                )
        ) {
            bw.write(totLine);
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    // 2
    public static void show() {
        String listRoot = "/Users/chryl/upload/";
        String filename = "fwrite.txt";

        List<String> stringList = new ArrayList<>();

        stringList.add("123456" + "\n");
        stringList.add("abcdefg" + "\n");

        String totLine = "";
        for (String line : stringList) {
            totLine = totLine + line;
        }


        File file = new File(listRoot + filename);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(listRoot + filename, true))) {

            bw.write(totLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
