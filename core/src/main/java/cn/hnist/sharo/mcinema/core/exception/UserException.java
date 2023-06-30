package cn.hnist.sharo.mcinema.core.exception;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;

public class UserException extends WithTypeException {
    private final Long serialVersionUID = 1L;
    @Override
    public ErrorEnum getType() {
        return super.getType();
    }

    public UserException(ErrorEnum type){
        super(type.getErrorMsg(),type);
    }

    public UserException(String message, ErrorEnum type) {
        super(message, type);
    }

    public UserException(String message, Throwable cause, ErrorEnum type) {
        super(message, cause, type);
    }

    public UserException(Throwable cause, ErrorEnum type) {
        super(cause, type);
    }
}
