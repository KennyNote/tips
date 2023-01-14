package com.learning.notebook.tips.leetcode.reverse_integer;

/**
 * @author Kenny Liu
 * @version 2019-10-31
 **/
public class Main {

    /**
     * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
     * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
     *
     * 解题思路：
     * 利用队列数据结构的思路，从最低位数到最高位数依次入队，先出栈的数字对应的反转后的数字位数越高。
     *      1 pop = x % 10 通过取余数的方式获得最低位数对应的数字，入队！
     *      2 x /= 10 通过整除的方式获取删掉最低位数后对应的数字。
     *      3 求当前次反转结果之前，需要对上次反转的结果进行溢出检查。
     *          3.1 rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)
     *              rev > Integer.MAX_VALUE/10
     *                  如果上次反转结果rev已经大于Integer.MAX_VALUE/10，rev = rev * 10 + pop的计算结果一定大于Integer.MAX_VALUE，溢出！
     *              rev == Integer.MAX_VALUE / 10 && pop > 7
     *                  如果上次反转结果rev恰好等于Integer.MAX_VALUE/10，rev = rev * 10 + pop的计算结果可能小于Integer.MAX_VALUE，所以接下就要看pop的数值，大于7，溢出！
     *          3.2 rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)
     *              rev < Integer.MIN_VALUE/10
     *                  如果上次反转结果rev已经小于Integer.MIN_VALUE/10，rev = rev * 10 + pop的计算结果一定小于Integer.MIN_VALUE，溢出！
     *              rev == Integer.MIN_VALUE / 10 && pop < -8
     *                  如果上次反转结果rev恰好等于Integer.MIN_VALUE/10，rev = rev * 10 + pop的计算结果可能大于Integer.MIN_VALUE，所以接下就要看pop的数值，小于-8，溢出！
     *      4 rev = rev * 10 + pop rev即为获得反转后的数字，通过循环判断 x != 0 循环构建反转数字的过程。
     *          4.1 如果 x /= 10 后 x != 0 说明x不可能是个位数，即还没有循环到最高位数。
     *          4.2 如果 x /= 10 后 x == 0 说明x只能是个位数，已经循环到最高位数了。
     */
    public int reverse_integer(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }
}
