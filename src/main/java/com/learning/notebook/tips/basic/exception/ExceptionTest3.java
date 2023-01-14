package com.learning.notebook.tips.basic.exception;

public class ExceptionTest3 {
    public static void main(String[] args){
        try{
            showException();
            System.out.println("A");
        } catch(Exception e){
            System.out.println("B");
        } finally{
            System.out.println("C");
        }
        System.out.println("D");
    }
    public static void showException() throws Exception{
        throw new Exception();
    }

    // 最终结果BCD

}
