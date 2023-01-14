package com.learning.notebook.tips.algorithm.bytedance;

/**
 * @author liuxuyang-001
 * @version 1.0
 * @date 2020/5/14 12:51
 */
public class Test2 {
    public int removeElement(int[] nums, int val) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            // 对于判断和val相等应该从index = 0判起
            if (nums[i] != val) {
                nums[j++] = nums[i];
            }
        }
        return j;
    }

    public int removeDuplicates(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        int j = 0;
        for (int i = 1; i < nums.length; i++)
            // 对于判断重复元素可以从index = 1判起，因为index = 0的元素一定是唯一的，从1开始和0判断，如果不相等即不重复，向前移位，j++,重复的话不移位。
        {
            if (nums[j] != nums[i]) {
                nums[++j] = nums[i];
            }
        }
        return ++j;
    }

    public static void main(String[] args) {
        Test2 test2 = new Test2();
        System.out.println(test2.removeElement(new int[]{3, 2, 2, 3}, 3));
    }
}