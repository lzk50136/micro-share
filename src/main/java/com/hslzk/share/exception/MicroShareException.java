package com.hslzk.share.exception;

public class MicroShareException extends RuntimeException {

    private static final long serialVersionUID = -2128385715605185108L;

    private Integer code;

    public MicroShareException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
