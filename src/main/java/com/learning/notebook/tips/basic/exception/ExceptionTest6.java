package com.learning.notebook.tips.basic.exception;

public class ExceptionTest6 {

    public int aaa1() {
        int x = 1;

        try {
//                        return ++x; // y = 2
            ++x; // y = 3
        } catch (Exception e) {

        } finally {
            ++x;
        }
        return x;
    }

    public int aaa2() {
        int x = 1;

        try {
            return ++x; // y = 2
            //            ++x; // y = 3
        } catch (Exception ignored) {

        } finally {
            ++x;
        }
        return x;
    }

    public static void main(String[] args) {
        ExceptionTest6 t = new ExceptionTest6();
        int y = t.aaa1();
        System.out.println(y);

        int z = t.aaa2();
        System.out.println(z);
    }

}
