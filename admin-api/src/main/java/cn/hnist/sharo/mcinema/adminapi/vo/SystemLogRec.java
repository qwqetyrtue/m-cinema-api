package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.db.pojo.SystemLog;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SystemLogRec extends SystemLog {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeUp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeDown;
    private Integer logType;
    private Integer adminId;

    public LocalDateTime getTimeUp() {
        return timeUp;
    }

    public void setTimeUp(LocalDateTime timeUp) {
        this.timeUp = timeUp;
    }

    public LocalDateTime getTimeDown() {
        return timeDown;
    }

    public void setTimeDown(LocalDateTime timeDown) {
        this.timeDown = timeDown;
    }

    @Override
    public Integer getLogType() {
        return logType;
    }

    @Override
    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    @Override
    public Integer getAdminId() {
        return adminId;
    }

    @Override
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
}
