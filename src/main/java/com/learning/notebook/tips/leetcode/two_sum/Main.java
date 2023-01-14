package com.learning.notebook.tips.leetcode.two_sum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kenny Liu
 * @version 2019-10-31
 **/
public class Main {

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     * 给定 nums = [2, 7, 11, 15], target = 9，因为 nums[0] + nums[1] = 2 + 7 = 9，所以返回 [0, 1]
     *
     * 解题思路：
     * 重点利用哈希表，通过以空间换取速度的方式，这种种更有效的方法来检查数组中是否存在目标元素。
     *      1 构建哈希数据结构，key目的存储数组元素本身，value目的存储数组元素下标。
     *      2 int complement = target - nums[i] 通过循环遍历，得到目标值和数组对应的元素值的差值。
     *      3 if (map.containsKey(complement)) 在每次循环中，我们把得到的差值进行检测，如果差值能和哈希表中的元素匹配，即找到目标值对应的两整数值，返回！
     *      4 map.put(nums[i], i) 进行到这一步说明还没有进行返回，把我们这次进行处理的数组元素存储到我们的哈希表中，为下一次检测做准备。
     */
    public int[] two_sum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}
