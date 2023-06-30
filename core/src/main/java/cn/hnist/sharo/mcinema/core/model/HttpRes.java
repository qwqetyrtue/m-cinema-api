package cn.hnist.sharo.mcinema.core.model;

/**
 * <h3>请求返回对象:
 * <ul>
 *     <li>code:返回状态</li>
 *     <li>msg:返回信息</li>
 *     <li>data:返回数据</li>
 * </ul>
 */
public class HttpRes {
    private int code;
    private String msg;
    private Object data;

    public HttpRes(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}