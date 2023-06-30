package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.SystemConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.function.Function;

public class SystemConstantRec extends SystemConstant implements VoRec<SystemConstant> {
    @NotBlank(message = "变量名不能为空", groups = {InsertBy.class, OneBy.Name.class})
    private String name;
    @NotBlank(message = "变量值不能为空", groups = {InsertBy.class})
    private String value;
    @NotNull(message = "变量编号不能为空", groups = {OneBy.PK.class})
    private Integer constantId;
    @NotNull(message = "参数不能为空", groups = {ListBy.Deleted.class})
    private Boolean deleted;

    @Override
    public SystemConstant adder(Function<SystemConstant, SystemConstant> set) {
        LocalDateTime now = LocalDateTime.now();
        SystemConstant add = new SystemConstantRec();
        add.setName(name);
        add.setValue(value);
        add.setUpdateTime(now);
        add.setCreateTime(now);
        add.setDeleted(false);
        return add;
    }

    public SystemConstant adder() {
        return adder(add -> add);
    }

    @Override
    public SystemConstant updater(Function<SystemConstant, SystemConstant> set) {
        SystemConstant update = new SystemConstantRec();
        // pk
        update.setConstantId(constantId);
        update.setName(name);
        // 更新修改时间
        update.setUpdateTime(LocalDateTime.now());
        return set.apply(update);
    }

    public SystemConstant updater() {
        return updater((update) -> {
            update.setDeleted(deleted);
            return update;
        });
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
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Integer getConstantId() {
        return constantId;
    }

    @Override
    public void setConstantId(Integer constantId) {
        this.constantId = constantId;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
