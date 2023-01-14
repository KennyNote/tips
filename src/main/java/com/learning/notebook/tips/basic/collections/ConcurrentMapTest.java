package com.learning.notebook.tips.basic.collections;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapTest {

    public static void main(String[] args) {
        System.out.println("123");
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.putIfAbsent("123","321");
        concurrentHashMap.putIfAbsent("321","123");

        List<String> naturalOrder = concurrentHashMap.values().stream().sorted(Comparator.naturalOrder()).toList();
        System.out.println(naturalOrder);

        List<String> reverseOrder = concurrentHashMap.values().stream().sorted(Comparator.reverseOrder()).toList();
        System.out.println(reverseOrder);

        System.out.println(concurrentHashMap);

        int i = (Integer.numberOfLeadingZeros(9) | (1 << (16 - 1)));
        String s = Integer.toBinaryString(i);
        System.out.println(i);
        System.out.println(s);
        System.out.println(Integer.numberOfLeadingZeros(4));
        System.out.println(Integer.toBinaryString(4));
        System.out.println(1 << (16 - 1));


    }
}
