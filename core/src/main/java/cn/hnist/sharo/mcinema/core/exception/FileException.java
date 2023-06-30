package cn.hnist.sharo.mcinema.core.exception;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;

public class FileException extends WithTypeException{
    private final Long serialVersionUID = 1L;
    private String fileName;

    public FileException(ErrorEnum type) {
        super(type);
    }

    public FileException(String message, ErrorEnum type) {
        super(message, type);
    }

    public FileException(String message, ErrorEnum type, String fileName) {
        super(message, type);
        this.fileName = fileName;
    }

    public FileException(String message, Throwable cause, ErrorEnum type, String fileName) {
        super(message, cause, type);
        this.fileName = fileName;
    }

    public FileException(Throwable cause, ErrorEnum type, String fileName) {
        super(cause, type);
        this.fileName = fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
