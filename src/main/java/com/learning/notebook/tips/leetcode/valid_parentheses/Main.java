package com.learning.notebook.tips.leetcode.valid_parentheses;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.junit.Test;

/**
 * @author Kenny Liu
 * @version 2019-10-31
 **/
public class Main {

    @Test
    public void test() {
//        Map<Character, Character> map = new HashMap<>();
//        map.put('(', ')');
//        map.put('[', ']');
//        map.put('{', '}');
//        System.out.println(" {[[]()]}".lastIndexOf(map.get('{')));
        System.out.println(isValid("{[[]()]}]"));
    }

    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     * 有效字符串需满足：
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     */
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                char topElement = stack.empty() ? '#' : stack.pop();
                if (topElement != map.get(c)) {
                    // 判断是否出栈的是自己
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    public boolean isValid1(String s) {
        if (s.isEmpty())
            return true;
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            System.out.println("stack = " + stack);
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.empty() || c != stack.pop())
                return false;
        }
        if (stack.empty())
            return true;
        return false;
    }

}
