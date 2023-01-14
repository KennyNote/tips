package com.learning.notebook.tips.leetcode.uniquePaths;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
//        System.out.println(main.isInterleave("c", "a", "ac"));
//        System.out.println(main.isInterleave("aabcc", "dbbca", "aadbbcbcac"));
                System.out.println(main.isInterleave("aabcc", "dbbca", "aadbbbaccc"));
    }

    public boolean isInterleave(String s1, String s2, String s3) {
        return lastMatch(s1, s2, s3, 0) == s3.length();
    }

    public int lastMatch(String s1, String s2, String s3, int j) {
        if (s1.equals("") && s2.equals("")) {
            return j;
        }

        int i = 0;
        while (i < s1.length()) {
            if (s1.charAt(i) == s3.charAt(j)) {
                i++;
                j++;
            } else {
                s1 = s1.substring(i);
                break;
            }
        }
        if (i == s1.length()) {
            return lastMatch(s2, "", s3, j);
        } else {
            return lastMatch(s2, s1, s3, j);


            //
        }

    }
    public int jump(int[] nums) {
        if (nums.length == 1) return 0;
        //前n-1个元素能够跳到的最远距离
        int k = 0;
        int count = 0;
        for (int i = 0; i <= k; i++) {
            //第i个元素能够跳到的最远距离
            int temp = i + nums[i];
            //更新最远距离
            k = Math.max(k, temp);
            //如果最远距离已经大于或等于最后一个元素的下标,则说明能跳过去,退出. 减少循环
            if (k >= nums.length - 1) {
                count = i +1;
                break;
            }
        }
        //最远距离k不再改变,且没有到末尾元素
        return count;
    }
}
