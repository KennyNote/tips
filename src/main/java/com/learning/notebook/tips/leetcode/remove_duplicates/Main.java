package com.learning.notebook.tips.leetcode.remove_duplicates;

/**
 * @author Kenny Liu
 * @version 2020-01-19
 **/
public class Main {

    public static void main(String[] args) {
        int[] data = {0,0,1,1,1,2,2,3,3,4};
        System.out.println(removeDuplicates(data));
    }

    public static int removeDuplicates(int[] nums) {
        // 使用双指针
        if (nums == null || nums.length == 1) {
            return nums.length;
        }
        int i = 0, j = 1;
        while (j < nums.length) {
            if (nums[i] == nums[j]) {
                j++;
            } else {
                i++;
                nums[i] = nums[j];
                j++;
            }
        }
        return i + 1;
    }

}
