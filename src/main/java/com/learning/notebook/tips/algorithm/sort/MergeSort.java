package com.learning.notebook.tips.algorithm.sort;

/**
 * 算法描述
 * 步骤1：把长度为n的输入序列分成两个长度为n/2的子序列；
 * 步骤2：对这两个子序列分别采用归并排序；
 * 步骤3：将两个排序好的子序列合并成一个最终的排序序列。
 **/
public class MergeSort {

    public static <T extends Comparable> void mergeSort(T[] list) {

    }

    public static void main(String[] args) {
        Integer[] a = {1, 3, 5, 9, 7, 8, 0, 2};
        mergeSort(a);
        for (int i : a)
            System.out.println(i);
    }

}
