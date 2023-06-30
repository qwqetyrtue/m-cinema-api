package cn.hnist.sharo.mcinema.userapi.config;

import cn.hnist.sharo.mcinema.core.exception.UserException;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static cn.hnist.sharo.mcinema.core.util.HttpResUtil.fail;

@RestControllerAdvice
@Order(1)
public class UserExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserExceptionHandler.class);

    // 配置数据验证处理
    @ResponseBody
    @ExceptionHandler(UserException.class)
    public HttpRes handlerMethodArgumentNotValidException(UserException e) {
        logger.error(e.getType() + e.getMessage());
        return fail(e.getMessage(), e.getType());
    }

}
