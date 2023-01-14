package com.learning.notebook.tips.algorithm.merge;

public class MergeArray {

    public static int[] merge(int[] arr1, int[] arr2) {
        int a = 0;
        int b = 0;
        int r = 0;
        int[] result = new int[arr1.length + arr2.length];
        while (a < arr1.length && b < arr2.length) {
            if (arr1[a] < arr2[b]) {
                result[r++] = arr1[a++];
            } else {
                result[r++] = arr2[b++];
            }
        }
        while (a < arr1.length) {
            result[r++] = arr1[a++];
        }
        while (b < arr2.length) {
            result[r++] = arr2[b++];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] a = new int[3];
        int[] b = new int[3];
        a[0] = 1;
        a[1] = 3;
        a[2] = 5;
        b[0] = 2;
        b[1] = 4;
        b[2] = 6;
        int[] merge = merge(a, b);
        for (int i : merge) {
            System.out.println(i);
        }

    }


}
