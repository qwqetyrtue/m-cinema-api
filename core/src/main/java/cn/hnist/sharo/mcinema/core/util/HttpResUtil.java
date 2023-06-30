package cn.hnist.sharo.mcinema.core.util;


import cn.hnist.sharo.mcinema.core.exception.WithTypeException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import com.github.pagehelper.PageInfo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HttpResUtil {

    public static HttpRes succeed(String msg, Object data) {
        return new HttpRes(0, msg, data);
    }

    public static HttpRes succeed(String msg) {
        return new HttpRes(0, msg, null);
    }

    public static HttpRes succeedList(String msg, PageInfo info) {
        Map<String, Object> data = new HashMap<>();
        data.put("list", info.getList()); // 数据
        data.put("total", info.getTotal()); // 总数据条数
        data.put("pageNum", info.getPageNum()); // 查询页数
        data.put("pageSize", info.getPageSize()); // 每页条数
        data.put("pages", info.getPages()); // 总分页数
        data.put("hasNextPage",info.isHasNextPage());
        data.put("nextPage",info.getNextPage());
        return new HttpRes(0, msg, data);
    }

    public static HttpRes succeedList(String msg, List<?> list) {
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        return new HttpRes(0, msg, data);
    }

    /**
     * <h3>处理fuc中可能抛出的错误</h3>
     * <li><strong>RuntimeException:</strong><br/>fail(ErrorEnum, e.getMessage())</li>
     * <li><strong>Exception:</strong><br/>fail(ErrorEnum.UNKNOWN_EXCEPTION)</li>
     *
     * @param fuc        要运行的函数
     * @param throwError 是否需要向外传递错误
     * @return HttpRes
     */
    public static HttpRes hasError(boolean throwError, Supplier<HttpRes> fuc) {
        try {
            return fuc.get();
        } catch (WithTypeException e) {
            return HttpResUtil.fail(e.getMessage(), throwError ? e : null, e.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION.getErrorMsg(), throwError ? e : null, ErrorEnum.UNKNOWN_EXCEPTION);
        }
    }
    public static HttpRes hasError(Supplier<HttpRes> fuc) {
        return hasError(false, fuc);
    }

    public static ResponseEntity<Object> hasErrorEntity(boolean throwError, Supplier<ResponseEntity<Object>> fuc) {
        try {
            return fuc.get();
        } catch (WithTypeException e) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(HttpResUtil.fail(e.getMessage(), throwError ? e : null, e.getType()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION.getErrorMsg(), throwError ? e : null, ErrorEnum.UNKNOWN_EXCEPTION));
        }
    }
    public static ResponseEntity<Object> hasErrorEntity(Supplier<ResponseEntity<Object>> fuc){
        return hasErrorEntity(false,fuc);
    }

    public static HttpRes fail(ErrorEnum errorType) {
        return new HttpRes(errorType.getCode(), errorType.getErrorMsg(), null);
    }

    public static HttpRes fail(String resMsg, ErrorEnum errorType) {
        return new HttpRes(errorType.getCode(), resMsg, null);
    }

    public static HttpRes fail(String resMsg, Object data, ErrorEnum errorType) {
        return new HttpRes(errorType.getCode(), resMsg, data);
    }

    public static HttpRes unLogin() {
        return fail("请登录", ErrorEnum.WITHOUT_AUTHENTICATED);
    }

    public static HttpRes unAuth() {
        return fail(ErrorEnum.WITHOUT_AUTHORIZED);
    }
}

