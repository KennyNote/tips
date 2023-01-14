package com.learning.notebook.tips.algorithm.sort;

import java.util.Comparator;

/**
 * @author Kenny Liu
 * @version 2020-01-06
 **/
public class ShellSort {

    public static <T> void sort(T[] list, Comparator<T> comp) {
        int gap = list.length / 2;
        while (gap > 0) {
            for (int i = gap; i < list.length; i++) {
                T temp = list[i];
                int pre_index = i - gap;
                while (pre_index >= 0 && comp.compare(list[pre_index], temp) > 0) {
                    list[pre_index + gap] = list[pre_index];
                    pre_index -= gap;
                }
                list[pre_index + gap] = temp;
            }
            gap /= 2;
        }
    }

    public static <T extends Comparable> void shellSort(T[] list) {
        int gap = list.length / 2;
        while (gap > 0) {
            for (int i = gap; i < list.length; i++) {
                T temp = list[i];
                int pre_index = i - gap;
                while (pre_index >= 0 && list[pre_index].compareTo(temp) > 0) {
                    list[pre_index + gap] = list[pre_index];
                    pre_index -= gap;
                }
                list[pre_index + gap] = temp;
            }
            gap /= 2;
        }
    }

    public static void main(String[] args) {
        Integer[] a = {1, 3, 5, 9, 7, 8, 0, 2};
        shellSort(a);
        for (int i : a)
            System.out.println(i);
    }

}
