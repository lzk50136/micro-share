package online.reiam.share.exception;

public class MicroShareException extends RuntimeException {

    private static final long serialVersionUID = -2128385715605185108L;

    private Integer code;

    public MicroShareException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public MicroShareException(String message) {
        super(message);
    }

    public Integer getCode() {
        return code;
    }

}
