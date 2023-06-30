package cn.hnist.sharo.mcinema.userapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.TicketBase;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.function.Function;

public class TicketRec extends TicketBase implements VoRec<TicketBase> {
    @NotNull(message = "场次编号不能为空",groups = {InsertBy.class,ListBy.Owner.class})
    private Long sceneId;
    @NotEmpty(message = "座位编号不能为空",groups = {InsertBy.class})
    private Integer[] InHallIndexList;
    @NotNull(message = "票务状态不能为空",groups = {ListBy.Status.class})
    private Short ticketStatus;

    @NotEmpty(message = "账单编号不能为空", groups = {UpdateFor.Order.class})
    private String orderId;

    @Override
    public TicketBase adder(Function<TicketBase, TicketBase> set) {
        return null;
    }

    @Override
    public TicketBase updater(Function<TicketBase, TicketBase> set) {
        return null;
    }


    @Override
    public Long getSceneId() {
        return sceneId;
    }

    @Override
    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public Integer[] getInHallIndexList() {
        return InHallIndexList;
    }

    public void setInHallIndexList(Integer[] inHallIndexList) {
        InHallIndexList = inHallIndexList;
    }

    @Override
    public Short getTicketStatus() {
        return ticketStatus;
    }

    @Override
    public void setTicketStatus(Short ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
