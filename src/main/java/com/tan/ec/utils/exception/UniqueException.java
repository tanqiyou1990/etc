
package com.tan.ec.utils.exception;

public class UniqueException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UniqueException() {
    }

    public UniqueException(String message) {
        super(message);
    }

    public UniqueException(Throwable cause) {
        super(cause);
    }

    public UniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
