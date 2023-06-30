package cn.hnist.sharo.mcinema.userapi.vo;

import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.AdvertisingBase;

import javax.validation.constraints.NotNull;
import java.util.function.Function;

public class AdvertisingRec extends AdvertisingBase implements VoRec<AdvertisingBase> {
    private Integer num = 7;

    @Override
    public AdvertisingBase adder(Function<AdvertisingBase, AdvertisingBase> set) {
        return null;
    }

    @Override
    public AdvertisingBase updater(Function<AdvertisingBase, AdvertisingBase> set) {
        return null;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
