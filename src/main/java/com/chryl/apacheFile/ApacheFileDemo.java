package com.chryl.apacheFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 写入文件
 * Created by Chr.yl on 2020/11/24.
 *
 * @author Chr.yl
 */
public class ApacheFileDemo {


    public static void main(String[] args) throws IOException {
        listFiles();
    }

    // 1 写入文件
    public static void writeFile1() {
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

    // 2 写入文件
    public static void writeFile2() {
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

    //读文件
    public static void readFileContent() throws IOException {
        File rFile = new File("/Users/chryl/upload/wfile.txt");
        String readFileToString = FileUtils.readFileToString(rFile, "gbk");
        System.out.println(readFileToString);

    }


    //根据后缀获得文件列表,不递归
    public static void listFiles() {
        Collection<File> listFiles =
                FileUtils.listFiles(new File("/Users/chryl/upload/"), FileFilterUtils.suffixFileFilter("txt"), null);
        System.out.println(listFiles);
    }

}
