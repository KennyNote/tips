package com.learning.notebook.tips.basic.exception;

public class ExceptionTest2 {

    public static void func() throws Exception{
        try{
            throw new Exception();
//             System.out.println("A");
            // throw单独存在，下面不要定义语句，因为执行不到，并且会编译不通过。
        }
        catch(Exception e){
            System.out.println("B");
        }
    }

    public static void main(String[] args){
        try{
            func();
        } catch(Exception e){
            System.out.println("C");
        }
        System.out.println("D");
    }


}
