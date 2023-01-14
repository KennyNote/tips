package com.learning.notebook.tips.leetcode.longest_common_prefix;

/**
 * @author Kenny Liu
 * @version 2019-10-31
 **/
public class Main {

    public String longestCommonPrefix(String[] strs) {
        if(strs.length == 0){
            return "";
        }
        String str = strs[0];
        for(int i = 1; i < strs.length; i++){
            while(strs[i].indexOf(str) != 0){
                str=str.substring(0, str.length() - 1);
            }
        }
        return str;
    }
}
