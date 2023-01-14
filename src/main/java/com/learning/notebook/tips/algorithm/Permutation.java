package com.learning.notebook.tips.algorithm;

public class Permutation {

    static int sum = 0;    // 不同方案总个数

    // 检查是否有同一国人连续3个
    public static boolean check(char[] c) {
        int count = 1;    // 初始个数
        for (int i = 0; i < c.length - 1; i++) {
            if (c[i] == c[i + 1]) {
                count++;
            } else {
                count = 1;    // 初始个数
            }
            if (count >= 3) {
                return true;
            }
        }
        return false;
    }

    // 全排列
    public static void allSort(char[] c, int start, int end) {
        if (start > end) {
            if (!check(c)) {    // 检查是否有同一国人连续3个
                System.out.println(c);
                sum++;        // 没有，方案总个数加1
            }
        } else {
            for (int i = start; i <= end; i++) {
                char temp = c[i];
                c[i] = c[start];
                c[start] = temp;
                allSort(c, start + 1, end);    // 递归
                temp = c[i];
                c[i] = c[start];
                c[start] = temp;

            }
        }
    }

    public static void main(String[] args) {
//        char[] pl = {'A','A','A','B','B','B','C','C','C'};
        char[] pl = {'A', 'B', 'C', 'D'};
        allSort(pl, 0, pl.length - 1);    // 全排列
        System.out.println(sum);
    }

}
