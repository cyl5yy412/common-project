package com.chryl;

import com.chryl.ruoyi.utils.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommonProjectApplicationTests {

    @Test
    void contextLoads() {

    }

    public static void main(String[] args) {
        String s = "/Users/chryl/upload/ry/profile/zzz/ssa";
        System.out.println(StringUtils.substringAfter(s, "/profile"));
        String downloadName = StringUtils.substringAfterLast(s, "/");
        System.out.println(downloadName);

    }

}
