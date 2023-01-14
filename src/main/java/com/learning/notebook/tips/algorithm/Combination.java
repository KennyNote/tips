package com.learning.notebook.tips.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Combination {

    public static List<String> combination(String s) {

        List<String> str = new ArrayList<>();
        char[] charArray = s.toCharArray();

        for (int i = 0; i < 1 << s.length(); i++) {
            StringBuilder string = new StringBuilder();
            for (int j = 0; j < s.length(); j++) {
                int tmp = 1 << j;
                if ((tmp & i) != 0) {
                    string.append(charArray[j]);
                }
            }
            str.add(string.toString());
        }
        return str;
    }

    public static void main(String[] args) {

        List<String> list = Combination.combination("abcd").stream().filter(string -> !string.isEmpty()).toList();
        System.out.println("结果数为:" + list.size());
        list.forEach(System.out::println);
    }

}
