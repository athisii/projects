package com.athisii.base.exception;

import java.io.Serial;

/**
 * @author athisii
 * @version 1.0
 * @since 12/10/21
 */

public class GenericException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 62938371762936942L;
    private Object data;

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
