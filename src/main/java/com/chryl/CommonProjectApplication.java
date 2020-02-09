package com.chryl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;
import java.util.function.Consumer;

@SpringBootApplication
public class CommonProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonProjectApplication.class, args);
        //java 8 打印
        Consumer consumer = System.out::println;
        consumer.accept("hello---");

        Student student = new Student("1", null);
        //java 8 判null,代替if判断
        String s = Optional.ofNullable(student)
                .map(Student::getB)
//                .map(Student::getA)
                .orElse(null);
        System.out.println(s);

    }

    public static class Student {
        private String a;
        private String b;

        public Student() {
        }

        public Student(String a, String b) {
            this.a = a;
            this.b = b;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }
}
