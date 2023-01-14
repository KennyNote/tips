package com.learning.notebook.tips.basic.exception.DIYException;

public class BusinessTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BusinessCheck t = new BusinessCheck();
        try {
            t.BusinessCheck("123456");
        } catch (BusinessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


}
