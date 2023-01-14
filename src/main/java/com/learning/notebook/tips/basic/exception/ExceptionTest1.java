package com.learning.notebook.tips.basic.exception;

public class ExceptionTest1 {

    public static void func() {
        try {
//            throw new Exception();
            // 没有catch时，不是运行时异常需要方法声明上throws Exception，否则编译不通过。
            throw new RuntimeException();
        } finally {
            System.out.println("B");
        }
    }

    public static void main(String[] args) {
        try {
            func();
            System.out.println("A");
        } catch (Exception e) {
            System.out.println("C");
        }
        System.out.println("D");
    }


}
