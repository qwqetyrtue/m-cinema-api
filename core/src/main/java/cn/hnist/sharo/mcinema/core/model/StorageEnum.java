package cn.hnist.sharo.mcinema.core.model;

import cn.hnist.sharo.mcinema.core.exception.FileException;
import cn.hnist.sharo.mcinema.core.exception.WithTypeException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StorageEnum {
    DOCUMENT("文档", "document", "DOCUMENT"),
    TEMP("临时文件", "temp", "TEMP"),
    IMAGE("图片", "image", "IMAGE");

    private final String desc;
    private final String path;
    private final String type;

    StorageEnum(String desc, String path, String type) {
        this.desc = desc;
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public String getDesc() {
        return desc;
    }

    public String getType() {
        return type;
    }

    public static List<String> getKeys() {
        return Arrays.stream(StorageEnum.values()).map(StorageEnum::getPath).collect(Collectors.toList());
    }


    public static StorageEnum parse(String key) throws WithTypeException {
        for (StorageEnum value : StorageEnum.values()) {
            if (value.path.equals(key))
                return value;
        }
        throw new FileException("文件类型错误", ErrorEnum.FAILED_LOAD);
    }
}
