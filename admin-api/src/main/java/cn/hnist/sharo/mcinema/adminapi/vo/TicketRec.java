package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.core.model.StatusEnum;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.TicketBase;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.function.Function;

public class TicketRec extends TicketBase implements VoRec<TicketBase> {
    @NotNull(message = "票据编号不能为空", groups = {OneBy.PK.class})
    private Long ticketId;
    @NotNull(message = "场次编号不能为空", groups = {InsertBy.class, UpdateFor._1.class})
    private Long sceneId;
    @NotNull(message = "座位编号不能为空", groups = {InsertBy.class, UpdateFor._1.class})
    private Long seatId;
    @NotNull(message = "用户编号不能为空", groups = {InsertBy.class, UpdateFor._2.class, ListBy._2.class})
    private Long userId;
    @NotNull(message = "票据状态不能为空", groups = {UpdateFor._3.class})
    private Short ticketStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeUp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeDown;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTimeUp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTimeDown;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundTimeUp;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundTimeDown;

    @NotNull(message = "影厅Id不能为空", groups = {ListBy._1.class})
    private Long hallId;

    @NotNull(message = "电影编号不能为空", groups = {ListBy._3.class})
    private Long filmId;

    @Override
    public TicketBase adder(Function<TicketBase, TicketBase> set) {
        TicketBase add = new TicketRec();
        add.setCreateTime(LocalDateTime.now());
        add.setSceneId(sceneId);
        add.setSeatId(seatId);
        add.setUserId(getUserId());
        add.setTicketStatus(StatusEnum.TicketStatus.PAID.v());
        return set.apply(add);
    }

    public TicketBase adder() {
        return adder(add -> add);
    }

    @Override
    public TicketBase updater(Function<TicketBase, TicketBase> set) {
        TicketBase update = new TicketBase();
        return set.apply(update);
    }

    public TicketBase updater() {
        return updater((update) -> {
            update.setUserId(getUserId());
            return update;
        });
    }

    public TicketBase sceneUpdater() {
        return updater((update) -> {
            update.setSceneId(sceneId);
            update.setSeatId(seatId);
            return update;
        });
    }

    public TicketBase statusUpdater() {
        return updater((update) -> {
            update.setTicketStatus(ticketStatus);
            if (ticketStatus.equals(StatusEnum.TicketStatus.REFUND.v()))
                update.setRefundTime(LocalDateTime.now());
            else if (ticketStatus.equals(StatusEnum.TicketStatus.USED.v()))
                update.setUseTime(LocalDateTime.now());
            else setTicketStatus(StatusEnum.TicketStatus.ACCIDENT.v());
            return update;
        });
    }

    @Override
    public Long getTicketId() {
        return ticketId;
    }

    @Override
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public Long getSeatId() {
        return seatId;
    }

    @Override
    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(Short ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    @Override
    public Long getSceneId() {
        return sceneId;
    }

    @Override
    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public LocalDateTime getCreateTimeUp() {
        return createTimeUp;
    }

    public void setCreateTimeUp(LocalDateTime createTimeUp) {
        this.createTimeUp = createTimeUp;
    }

    public LocalDateTime getCreateTimeDown() {
        return createTimeDown;
    }

    public void setCreateTimeDown(LocalDateTime createTimeDown) {
        this.createTimeDown = createTimeDown;
    }

    public LocalDateTime getUseTimeUp() {
        return useTimeUp;
    }

    public void setUseTimeUp(LocalDateTime useTimeUp) {
        this.useTimeUp = useTimeUp;
    }

    public LocalDateTime getUseTimeDown() {
        return useTimeDown;
    }

    public void setUseTimeDown(LocalDateTime useTimeDown) {
        this.useTimeDown = useTimeDown;
    }

    public LocalDateTime getRefundTimeUp() {
        return refundTimeUp;
    }

    public void setRefundTimeUp(LocalDateTime refundTimeUp) {
        this.refundTimeUp = refundTimeUp;
    }

    public LocalDateTime getRefundTimeDown() {
        return refundTimeDown;
    }

    public void setRefundTimeDown(LocalDateTime refundTimeDown) {
        this.refundTimeDown = refundTimeDown;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }
}
