package com.learning.notebook.tips.algorithm.bytedance;

import java.util.Scanner;

/**
 * @author liuxuyang-001
 * @version 1.0
 * @date 2020/5/14 12:47
 */
public class Test1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            String str = sc.next();
            System.out.println(processString(str));
        }
    }

    public static String processString(String str) {
        int i = 0;
        while (i < str.length() - 2) {
            if (str.charAt(i) == str.charAt(i + 1) && str.charAt(i) == str.charAt(i + 2)) {
                str = str.substring(0, i + 2) + str.substring(i + 3);
            } else {
                i++;
            }
        }
        i = 0;
        while (i < str.length() - 3) {
            if (str.charAt(i) == str.charAt(i + 1) && str.charAt(i + 2) == str.charAt(i + 3)) {
                str = str.substring(0, i + 3) + str.substring(i + 4);
            } else {
                i++;
            }
        }
        return str;
    }
}
