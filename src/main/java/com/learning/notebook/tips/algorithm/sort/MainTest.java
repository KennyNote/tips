package com.learning.notebook.tips.algorithm.sort;

import org.junit.Test;

/**
 * @author Kenny Liu
 * @version 2020-01-03
 **/
public class MainTest {

    @Test
    public void testBubbleSort(){
        Integer[] a = {1, 3, 5, 9, 7, 8, 0, 2};
        BubbleSorter.bubbleSort(a);
        for (int i : a)
            System.out.println(i);
    }

    @Test
    public void testSelectSort(){
        Integer[] a = {1, 3, 5, 9, 7, 8, 0, 2};
        SelectSorter.selectSort(a);
        for (int i : a)
            System.out.println(i);
    }

    @Test
    public void testInsertSort(){
        Integer[] a = {1, 3, 5, 9, 7, 8, 0, 2};
        InsertSorter.insertSort(a);
        for (int i : a)
            System.out.println(i);
    }

    @Test
    public void testShellSort(){
        Integer[] a = {1, 3, 5, 9, 7, 8, 0, 2};
        ShellSort.shellSort(a);
        for (int i : a)
            System.out.println(i);
    }

}
