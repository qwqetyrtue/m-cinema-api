package cn.hnist.sharo.mcinema.adminapi.vo;

import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.core.vo.VoRec;
import cn.hnist.sharo.mcinema.db.pojo.AdvertisingBase;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.function.Function;

public class AdvertisingRec extends AdvertisingBase implements VoRec<AdvertisingBase> {
    @NotNull(message = "广告编号不能为空", groups = {OneBy.PK.class})
    private Long advertisingId;
    @NotEmpty(message = "名称不能为空", groups = {InsertBy.class, ListBy.Name.class})
    private String name;
    @NotEmpty(message = "链接不能为空", groups = {InsertBy.class})
    private String link;
    @NotEmpty(message = "文字不能为空", groups = {InsertBy.class})
    private String text;

    @Override
    public AdvertisingBase adder(Function<AdvertisingBase, AdvertisingBase> set) {
        AdvertisingBase add = new AdvertisingRec();
        add.setCreateTime(LocalDateTime.now());
        add.setName(name);
        add.setLink(link);
        add.setText(text);
        return set.apply(add);
    }

    public AdvertisingBase adder(String img) {
        return adder(add -> {
            add.setImg(img);
            return add;
        });
    }

    @Override
    public AdvertisingBase updater(Function<AdvertisingBase, AdvertisingBase> set) {
        AdvertisingBase update = new AdvertisingRec();
        update.setAdvertisingId(advertisingId);
        return set.apply(update);
    }

    public AdvertisingBase updater() {
        return updater(update -> {
            update.setName(name);
            update.setLink(link);
            update.setText(text);
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
    public String getLink() {
        return link;
    }

    @Override
    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Long getAdvertisingId() {
        return advertisingId;
    }

    @Override
    public void setAdvertisingId(Long advertisingId) {
        this.advertisingId = advertisingId;
    }
}
