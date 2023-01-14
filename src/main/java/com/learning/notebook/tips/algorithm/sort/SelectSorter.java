package com.learning.notebook.tips.algorithm.sort;

import java.util.Comparator;

/**
 * 选择排序
 */
public class SelectSorter {

    public static <T> void sort(T[] list, Comparator<T> comp) {
        for (int i = 0; i < list.length; ++i) {
            int min_index = i;
            for (int j = i + 1; j < list.length; ++j) {
                if (comp.compare(list[min_index], list[j]) > 0) //找到最小的数
                    min_index = j; //将最小数的索引保存
            }
            T temp = list[min_index];
            list[min_index] = list[i];
            list[i] = temp;
        }
    }

    public static <T extends Comparable> void selectSort(T[] list) {
        for (int i = 0; i < list.length; ++i) {
            int min_index = i;
            for (int j = i + 1; j < list.length; ++j) {
                if (list[min_index].compareTo(list[j]) > 0) //找到最小的数
                    min_index = j; //将最小数的索引保存
            }
            T temp = list[min_index];
            list[min_index] = list[i];
            list[i] = temp;
        }
    }

    public static void main(String[] args) {
        Integer[] a = {1, 3, 5, 9, 7, 8, 0, 2};
        selectSort(a);
        for (int i : a)
            System.out.println(i);
    }

}
