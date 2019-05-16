package org.springframework.exceptions;

public  class BeansException extends RuntimeException {


    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable t) {
        super(msg, t);
    }


}
