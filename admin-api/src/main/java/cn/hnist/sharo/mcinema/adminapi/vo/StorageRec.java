package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.StorageBase;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.function.Function;

public class StorageRec extends StorageBase implements VoRec<StorageBase>  {
    @NotEmpty(message = "资源类型不能为空",groups = {ListBy.Filter.class})
    private String type;

    @Override
    public StorageBase adder(Function<StorageBase, StorageBase> set) {
        return null;
    }

    @Override
    public StorageBase updater(Function<StorageBase, StorageBase> set) {
        return null;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }
}
