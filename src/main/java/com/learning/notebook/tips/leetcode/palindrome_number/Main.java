package com.learning.notebook.tips.leetcode.palindrome_number;

import org.junit.Test;

/**
 * @author Kenny Liu
 * @version 2019-10-31
 **/
public class Main {

    @Test
    public void test(){
        int x = -0;
        System.out.println(is_palindrome(x));
    }

    /**
     * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     * 输入: 121 输出: true；输入: -121 输出: false；输入: 10 输出: false
     *
     * 解题思路：
     * 这个和反转数字的问题类似，但是反转数字的目的是获得最后反转的数字，而对于回文数，我们关注的点在于数字是否满足对称性，
     * 所以只需要关注反转数字一半的过程，一半的过程也不需要关注数字溢出的问题。
     *      1 x < 0 || (x % 10 == 0 && x != 0)，首先排除不符合条件的数字。
     *      2 while (x > rev) 因为只需要关注一半的过程，只需要进行到 x <= rev就可以停止。
     *      3 rev = rev * 10 + x % 10 没有了溢出判断，可以写的更加紧凑。
     *      4 x == rev || x == rev/10 最终判断，当数字长度为奇数时，我们可以通过 rev/10 去除处于中位的数字
     *
     */
    public boolean is_palindrome(int x) {
        if(x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int rev = 0;
        while (x > rev) {
            rev = rev * 10 + x % 10;
            x /= 10;
        }
        return x == rev || x == rev/10;
    }
}
