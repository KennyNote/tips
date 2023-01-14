package com.learning.notebook.tips.basic;

public class Test1 {

    public static void main(String[] args) {

        Long[][] longs = new Long[1024 * 1024][6];
        for (int i = 0; i < 1024 * 1024; i++) {
            for (int j = 0; j < 6; j++) {
                longs[i][j] = 1L;
            }
        }

        long l1 = System.currentTimeMillis();
        long sum = 0;
        for (int i = 0; i < 1024 * 1024; i++) {
            for (int j = 0; j < 6; j++) {
                sum += longs[i][j];
            }
        }
        long l2 = System.currentTimeMillis();
        System.out.println(l2 - l1 + " for 1");
        System.out.println(sum + " for 1");

        l1 = System.currentTimeMillis();
        sum = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 1024 * 1024; j++) {
                sum += longs[j][i];
            }
        }
        l2 = System.currentTimeMillis();
        System.out.println(l2 - l1 + " for 2");
        System.out.println(sum + " for 2");
    }
}
