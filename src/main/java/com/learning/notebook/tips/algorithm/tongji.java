package com.learning.notebook.tips.algorithm;

import java.util.Scanner;

public class tongji {

    public static void stringStatistics(String str) {
        int bigchar = 0, smallchar = 0, number = 0, blank = 0, other = 0;
        int n = str.length();
        for (int i = 0; i < n; i++) {
			if (str.charAt(i) == 32)//空格
			{
				blank++;
			} else if (str.charAt(i) >= 48 && str.charAt(i) <= 57)//数字
			{
				number++;
			} else if (str.charAt(i) >= 65 && str.charAt(i) <= 90)//大写字母
			{
				bigchar++;
			} else if (str.charAt(i) >= 97 && str.charAt(i) <= 122)//小写字母
			{
				smallchar++;
			} else {
				other++;
			}
        }
        System.out.println(str);
        System.out.println("大写字母个数：" + bigchar);
        System.out.println("小写字母个数：" + smallchar);
        System.out.println("数字个数：" + number);
        System.out.println("空格个数：" + blank);
        System.out.println("其他字符个数：" + other);
    }

    public static void main(String[] args) {

        Scanner sca = new Scanner(System.in);
        String str = sca.nextLine();
        stringStatistics(str);


    }
}
