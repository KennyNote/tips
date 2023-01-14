package com.learning.notebook.tips.algorithm.sort;

import java.util.Comparator;

/**
 * 冒泡排序
 */
public class BubbleSorter {

    public static <T> void sort(T[] list, Comparator<T> comp) {
        boolean swapped = true;
        for (int i = 0, len = list.length; i < len && swapped; ++i) {
            swapped = false;
            for (int j = 0; j < len - i - 1; ++j) {
                if (comp.compare(list[j], list[j + 1]) > 0) {
                    T temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    swapped = true;
                }
            }
        }
    }

    public static <T extends Comparable> void bubbleSort(T[] list) {
        boolean flag = true;
        for (int i = 0, len = list.length; i < len && flag; ++i) {
            flag = false;
            for (int j = 0; j < len - i - 1; ++j) { // len - i - 1的目的就是最后T[len - i - 1]位都是确认有序的，不用参与循环进行判断。
                if (list[j].compareTo(list[j + 1]) > 0) {
                    T temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    flag = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[] a = {1, 3, 5, 9, 7, 8, 0, 2};
        bubbleSort(a);
        for (int i : a)
            System.out.println(i);
    }

}
