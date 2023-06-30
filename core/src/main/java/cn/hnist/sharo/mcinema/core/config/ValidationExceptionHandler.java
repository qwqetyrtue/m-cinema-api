package cn.hnist.sharo.mcinema.core.config;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.StringJoiner;

import static cn.hnist.sharo.mcinema.core.util.HttpResUtil.fail;

@RestControllerAdvice
@Order(2)
public class ValidationExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ValidationExceptionHandler.class);

    // 配置数据验证处理
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpRes handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 错误信息校验
        boolean fieldErrorUnobtainable = e == null || CollectionUtils.isEmpty(e.getBindingResult().getAllErrors()) || e.getBindingResult().getAllErrors().get(0) == null;
        if (fieldErrorUnobtainable) {
            return fail(ErrorEnum.UNKNOWN_EXCEPTION);
        }

        // 获取错误信息
        List<ObjectError> fieldError = e.getBindingResult().getAllErrors();

        StringJoiner stringJoiner = new StringJoiner(",");

        for (ObjectError each : fieldError) {
            stringJoiner.add(each.getDefaultMessage());

        }

        // 返回
        return fail(stringJoiner.toString(), ErrorEnum.WRONG_PARAMS_FORMAT);
    }

    @ExceptionHandler(BindException.class)
    public HttpRes handlerBindException(BindException e) {
        // 错误信息校验
        boolean fieldErrorUnobtainable = e == null || CollectionUtils.isEmpty(e.getBindingResult().getAllErrors()) || e.getBindingResult().getAllErrors().get(0) == null;
        if (fieldErrorUnobtainable) {
            return fail(ErrorEnum.UNKNOWN_EXCEPTION);
        }

        // 获取错误信息
        List<ObjectError> fieldError = e.getBindingResult().getAllErrors();

        StringJoiner stringJoiner = new StringJoiner(",");

        for (ObjectError each : fieldError) {
            stringJoiner.add(each.getDefaultMessage());

        }

        // 返回
        return fail(stringJoiner.toString(), ErrorEnum.WRONG_PARAMS_FORMAT);
    }

}


