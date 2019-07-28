
package com.tan.ec.utils.exception;

/**
 * 403 授权拒绝
 */
public class VpDeniedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VpDeniedException() {
    }

    public VpDeniedException(String message) {
        super(message);
    }

    public VpDeniedException(Throwable cause) {
        super(cause);
    }

    public VpDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public VpDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
