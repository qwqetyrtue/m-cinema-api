package cn.hnist.sharo.mcinema.core.model;

public enum FilmConditionEnum {

    FILM("film",(short) 1),
    POST("post",(short) 2);

    private final String condition;
    private final Short value;

    FilmConditionEnum(String condition, Short value) {
        this.condition = condition;
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public Short getValue() {
        return value;
    }
}
