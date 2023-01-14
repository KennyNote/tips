package com.learning.notebook.tips.leetcode.roman_to_integer;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * @author Kenny Liu
 * @version 2019-10-31
 **/
public class Main {

    @Test
    public void test() {
        System.out.println(romanToInt("MCMXCIV"));
    }

    /**
     * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 通常情况下，罗马数字中小的数字在大的数字的右边。
     * 特殊的规则只适用于以下六种情况：
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
     *
     * 解题思路：
     *      1 首先将所有的组合可能性列出并添加到哈希表中
     *      2 然后对字符串进行遍历，由于组合只有两种，一种是 1 个字符，一种是 2 个字符，其中 2 个字符优先于 1 个字符
     *      3 先判断两个字符的组合在哈希表中是否存在，存在则将值取出加到结果 result 中，并向后移2个字符。不存在则将判断当前 1 个字符是否存在，存在则将值取出加到结果 result 中，并向后移 1 个字符
     *      4 遍历结束返回结果 result
     */
    public int romanToInt(String s) {
        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);

        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i + 1 < s.length() && map.containsKey(s.substring(i, i + 2))) {
                result += map.get(s.substring(i, i + 2));
                i += 1;
            } else {
                result += map.get(s.substring(i, i + 1));
            }
        }
        return result;
    }

}
