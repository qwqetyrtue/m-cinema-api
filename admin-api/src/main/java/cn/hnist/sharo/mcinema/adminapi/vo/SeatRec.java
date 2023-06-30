package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.HallSeat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.function.Function;

public class SeatRec extends HallSeat implements VoRec<HallSeat> {
    @NotNull(message = "座位编号不能为空", groups = {OneBy.PK.class})
    private Long seatId;
    @NotNull(message = "影厅编号不能为空", groups = {InsertBy.Batch.class, InsertBy.class, ListBy.Filter.class})
    private Long hallId;
    @NotNull(message = "厅内序号不能为空", groups = {OneBy.Index.class, InsertBy.class})
    private Integer inHallIndex;
    @NotNull(message = "参数缺失", groups = {})
    private Boolean deleted;

    private Integer level;

    @NotEmpty(message = "座位编号列表不能为空", groups = {UpdateFor.Batch.class})
    private Long[] seatIdList;

    @Override
    public HallSeat updater(Function<HallSeat, HallSeat> set) {
        HallSeat update = new HallSeat();
        // pk
        update.setSeatId(seatId);
        // 可修改项
        return set.apply(update);
    }

    public HallSeat classifyUpdater() {
        return updater((update) -> {
            update.setLevel(level);
            return update;
        });
    }

    @Override
    public HallSeat adder(Function<HallSeat, HallSeat> set) {
        HallSeat add = new HallSeat();
        add.setCreateTime(LocalDateTime.now());
        return set.apply(add);
    }

    public HallSeat oneAdder() {
        return adder((add) -> {
            add.setHallId(hallId);
            add.setInHallIndex(inHallIndex);
            add.setLevel(level);
            add.setDeleted(false);
            return add;
        });
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public Integer getInHallIndex() {
        return inHallIndex;
    }

    @Override
    public void setInHallIndex(Integer inHallIndex) {
        this.inHallIndex = inHallIndex;
    }

    @Override
    public Long getHallId() {
        return hallId;
    }

    @Override
    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    @Override
    public Long getSeatId() {
        return seatId;
    }

    @Override
    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long[] getSeatIdList() {
        return seatIdList;
    }

    public void setSeatIdList(Long[] seatIdList) {
        this.seatIdList = seatIdList;
    }
}
