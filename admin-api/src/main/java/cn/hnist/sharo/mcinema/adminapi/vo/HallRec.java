package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.util.HallSeatUtil;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.HallBase;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.function.Function;

public class HallRec extends HallBase implements VoRec<HallBase> {

    @NotNull(message = "影厅Id不能为空", groups = {OneBy.PK.class})
    private Long hallId;

    @NotBlank(message = "名称不能为空", groups = {InsertBy.Draft.class, ListBy.Name.class})
    @Size(max = 15, min = 3, message = "名称长度格式错误", groups = {InsertBy.Draft.class, UpdateFor.Profile.class})
    private String name;
    @NotNull(message = "影厅类型不能为空", groups = {InsertBy.Draft.class})
    private String hallType;
    @NotNull(message = "影厅座位排布不能为空", groups = {UpdateFor.Secure.class})
    private Integer[][] seatArrange;
    @NotNull(message = "座位最大行数不能为空", groups = {UpdateFor.Secure.class})
    @Range(min = 0, max = 50, message = "座位行数值超出范围", groups = {UpdateFor.Secure.class})
    private Integer rowNum;
    @NotNull(message = "座位最大列数不能为空", groups = {UpdateFor.Secure.class})
    @Range(min = 0, max = 50, message = "座位最大列数值超出范围", groups = {UpdateFor.Secure.class})
    private Integer columnNum;

    private Integer seatNumUp;
    private Integer seatNumDown;

    @Override
    public HallBase updater(Function<HallBase, HallBase> set) {
        HallBase update = new HallBase();
        // pk
        update.setHallId(hallId);
        //更新修改时间
        update.setUpdateTime(LocalDateTime.now());
        // 可修改项
        return set.apply(update);
    }

    public HallBase profileUpdater() {
        return updater((update) -> {
            update.setName(name);
            update.setLocation(getLocation());
            update.setHallType(hallType);
            return update;
        });
    }

    public HallBase arrangeUpdater() {
        if (!DataUtil.checkMatrix(seatArrange, rowNum, columnNum))
            throw new DatabaseException("座位排布格式错误", ErrorEnum.CONSTRAINT_EXCEPTION);
        return updater((update) -> {
            int setNum = HallSeatUtil.getSeatNum(seatArrange);
            update.setSeatArrange(seatArrange);
            update.setColumnNum(columnNum);
            update.setRowNum(rowNum);
            update.setSeatNum(setNum);
            return update;
        });
    }


    @Override
    public HallBase adder(Function<HallBase, HallBase> set) {
        LocalDateTime now = LocalDateTime.now();
        HallBase add = new HallBase();
        add.setName(name);
        add.setHallType(hallType);
        add.setCreateTime(now);
        add.setUpdateTime(now);
        return set.apply(add);
    }

    public HallBase draftAdder() {
        return adder((add) -> {
            add.setDeleted(true);
            return add;
        });
    }

    public Integer getSeatNumUp() {
        return seatNumUp;
    }

    public void setSeatNumUp(Integer seatNumUp) {
        this.seatNumUp = seatNumUp;
    }

    public Integer getSeatNumDown() {
        return seatNumDown;
    }

    public void setSeatNumDown(Integer seatNumDown) {
        this.seatNumDown = seatNumDown;
    }

    @Override
    public Integer getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public Integer getColumnNum() {
        return columnNum;
    }

    @Override
    public void setColumnNum(Integer columnNum) {
        this.columnNum = columnNum;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getHallType() {
        return hallType;
    }

    @Override
    public void setHallType(String hallType) {
        this.hallType = hallType;
    }

    @Override
    public Integer[][] getSeatArrange() {
        return seatArrange;
    }

    @Override
    public void setSeatArrange(Integer[][] seatArrange) {
        this.seatArrange = seatArrange;
    }


}
