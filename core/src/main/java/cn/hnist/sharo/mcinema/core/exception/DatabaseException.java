package cn.hnist.sharo.mcinema.core.exception;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;

public class DatabaseException extends WithTypeException {
    private final Long serialVersionUID = 1L;

    public DatabaseException(ErrorEnum type) {
        super(type);
    }

    public DatabaseException(String message, ErrorEnum type) {
        super(message, type);
    }

    public DatabaseException(String message, Throwable cause, ErrorEnum type) {
        super(message, cause, type);
    }

    public DatabaseException(Throwable cause, ErrorEnum type) {
        super(cause, type);
    }
}
