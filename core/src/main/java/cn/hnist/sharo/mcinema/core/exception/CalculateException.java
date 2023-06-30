package cn.hnist.sharo.mcinema.core.exception;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;

public class CalculateException extends WithTypeException{
    private final Long serialVersionUID = 1L;
    private Long ticketId;

    public CalculateException(ErrorEnum type) {
        super(type);
    }

    public CalculateException(String message, ErrorEnum type, Long ticketId) {
        super(message, type);
        this.ticketId = ticketId;
    }

    public CalculateException(String message, Throwable cause, ErrorEnum type, Long ticketId) {
        super(message, cause, type);
        this.ticketId = ticketId;
    }

    public CalculateException(Throwable cause, ErrorEnum type, Long ticketId) {
        super(cause, type);
        this.ticketId = ticketId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
