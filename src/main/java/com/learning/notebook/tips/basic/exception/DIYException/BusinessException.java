package com.learning.notebook.tips.basic.exception.DIYException;

import java.io.Serializable;

public class BusinessException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 2744159312265350143L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

}
