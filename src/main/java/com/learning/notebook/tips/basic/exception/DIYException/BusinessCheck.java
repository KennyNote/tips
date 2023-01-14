package com.learning.notebook.tips.basic.exception.DIYException;

public class BusinessCheck {

    public void BusinessCheck(String str) throws BusinessException {

        if (str.length() <= 5) {
            System.out.println("长度OK");
        } else {
            //抛出自定义异常信息
            throw new BusinessException("长度过长");
        }

    }


}
