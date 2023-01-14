package com.learning.notebook.tips.basic.string;

import java.util.Arrays;
import org.junit.Test;

public class Main {

    public static void test1() {
        String s1 = "Programming";
        String s2 = "Program" + "ming";
        System.out.println(s1 == s2); // true
    }

    public static void test2() {
        String s1 = "Programming";
        String s2 = "Program";
        String s3 = "ming";
        String s4 = s2 + s3;
        System.out.println(s1 == s4); // false
        System.out.println(s1 == s4.intern()); //true
        System.out.println(s4 == s4.intern()); //false
    }

    public static void test3() {
        String s1 = "Programming";// s1指向常量池中的"Programming"
        String s2 = new String("Programming");
        System.out.println(s1 == s2); // false
        System.out.println(s1 == s2.intern()); //true
        System.out.println(s2 == s2.intern()); //false
    }

    public static void test4(){
        String a = "123";
        String b = "123";
        String c = new String("123");
        String d = new String("123");
        System.out.println(a == b); // true
        System.out.println(a.equals(b)); // true
        System.out.println(a.equals(c)); // true
        System.out.println(c == d); // false
        System.out.println(c.equals(d)); // true
        System.out.println(a == c); // false

    }
    
    public static void test5(){
        String s1 = new String("1");    // 同时会生成堆中的对象 以及常量池中1的对象，但是此时s1是指向堆中的对象的
        s1.intern();            // 常量池中的已经存在
        String s2 = "1";
        System.out.println(s1 == s2);    // false
        System.out.println(s1.intern() == s2); //true


        String s3 = new String("2") + new String("2");
        String s4 = "22";        // 常量池中不存在22，所以会新开辟一个存储22对象的常量池地址
        s3.intern();    // 常量池22的地址和s3的地址不同
        System.out.println(s3 == s4); // false
        System.out.println(s3.intern() == s4); // true
    }

    public static void test6(){
        // String 类中除了 split 方法外，有正则表达式接口的方法都是调用 Pattern（模式类）和 Matcher（匹配器类）进行实现的。
        System.out.println("1,2".split(",").length); // 2
        System.out.println("1,2,".split(",").length); // 2
        System.out.println("".split(",").length);// 1
        System.out.println(",".split(",").length);// 0

        System.out.println(Arrays.toString("m.g.h.i.o".split("."))); // []
        System.out.println(Arrays.toString("m|g|h|i|o".split("|")));// [m, |, g, |, h, |, i, |, o]

        System.out.println(Arrays.toString("m.g.h.i.o".split("\\.")));// [m, g, h, i, o]
        System.out.println(Arrays.toString("m|g|h|i|o".split("\\|")));// [m, g, h, i, o]

        System.out.println(Arrays.toString("m_g_h_i_o".split("_")));// [m, g, h, i, o]
        System.out.println(Arrays.toString("m_g_h_i_o__".split("_")));// [m, g, h, i, o]
        System.out.println(Arrays.toString("m__g_h_i_o_".split("_")));// [m, , g, h, i, o]

        System.out.println(Arrays.toString("m_g_h_i_o".split("_", -1)));// [m, g, h, i, o]
        System.out.println(Arrays.toString("m_g_h_i_o__".split("_", -1)));// [m, g, h, i, o, , ]
        System.out.println(Arrays.toString("m__g_h_i_o_".split("_", -1)));// [m, , g, h, i, o, ]
    }

    public static void test7(){
        StringBuffer stringBuffer = new StringBuffer("select mg.orgId from MgmtOrganization mg where 1=1 and mg.orgcode2=:breach");
        System.out.println(stringBuffer.length());
        stringBuffer.setLength(0);
        System.out.println(stringBuffer.length());
    }


    public static void main(String[] args) {
        test4();
    }
}
