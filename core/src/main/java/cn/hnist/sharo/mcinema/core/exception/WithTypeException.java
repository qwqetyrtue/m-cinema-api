package cn.hnist.sharo.mcinema.core.exception;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;

public abstract class WithTypeException extends RuntimeException {
    private final Long serialVersionUID = 1L;
    private ErrorEnum type;

    public WithTypeException(ErrorEnum type) {
        super(type.getErrorMsg());
        this.type = type;
    }

    public WithTypeException(String message, ErrorEnum type) {
        super(message);
        this.type = type;
    }

    public WithTypeException(String message, Throwable cause, ErrorEnum type) {
        super(message, cause);
        this.type = type;
    }

    public WithTypeException(Throwable cause, ErrorEnum type) {
        super(cause);
        this.type = type;
    }

    public ErrorEnum getType() {
        return type;
    }

    public void setType(ErrorEnum type) {
        this.type = type;
    }
}
