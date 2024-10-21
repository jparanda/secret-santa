package com.juan.cc.exceptions;

public class NoValidReceiverException extends RuntimeException {

    public NoValidReceiverException(String message) {
        super(message);
    }

    public NoValidReceiverException(String message, Throwable cause) {
        super(message, cause);
    }
}
