package cn.hnist.sharo.mcinema.core.model;

import cn.hnist.sharo.mcinema.core.exception.FileException;
import cn.hnist.sharo.mcinema.core.exception.WithTypeException;

public enum VoteEnum {
    UPVOTE("upvote", (short) 1),
    DOWNVOTE("downvote", (short) 2);

    private final String type;
    private final Short value;

    VoteEnum(String type, Short value) {
        this.type = type;
        this.value = value;
    }

    public static VoteEnum parse(String key){
        for (VoteEnum value : VoteEnum.values()) {
            if (value.type.equals(key))
                return value;
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public Short getValue() {
        return value;
    }
}
