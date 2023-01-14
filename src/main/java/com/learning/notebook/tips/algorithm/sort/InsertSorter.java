package com.learning.notebook.tips.algorithm.sort;

import java.util.Comparator;

/**
 * 插入排序
 * 具体算法描述如下：
 * 步骤1: 从第一个元素开始，该元素可以认为已经被排序；
 * 步骤2: 取出下一个元素，用额外的变量进行记录，并在已经排序的元素序列中从后向前扫描；
 * 步骤3: 如果该元素（已排序）大于新元素，将该元素移到下一位置；
 * 步骤4: 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；Tips：因为之前之前的序列以排序，只会出现一次插入操作。
 * 步骤5: 将新元素插入到该位置后；
 * 步骤6: 重复步骤2~5。
 */
public class InsertSorter {

    public static <T> void sort(T[] list, Comparator<T> comp) {
        T current;
        for (int i = 0; i < list.length - 1; i++) {
            current = list[i + 1];
            int pre_index = i;
            while (pre_index >= 0 && comp.compare(list[pre_index], current) > 0) {
                list[pre_index + 1] = list[pre_index];
                pre_index--;
            }
            list[pre_index + 1] = current;
        }
    }

    public static <T extends Comparable> void insertSort(T[] list) {
        T current;
        for (int i = 0; i < list.length - 1; i++) {
            current = list[i + 1];
            int pre_index = i;
            while (pre_index >= 0 && list[pre_index].compareTo(current) > 0) {
                list[pre_index + 1] = list[pre_index];
                pre_index--;
            }
            list[pre_index + 1] = current;
        }
    }

    public static void main(String[] args) {
        Integer[] a = {1, 3, 5, 9, 7, 8, 0, 2};
        insertSort(a);
        for (int i : a)
            System.out.println(i);
    }

}
