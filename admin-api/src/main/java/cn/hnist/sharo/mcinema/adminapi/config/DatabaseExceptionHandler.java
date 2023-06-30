package cn.hnist.sharo.mcinema.adminapi.config;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@RestControllerAdvice
@Order(2)
public class DatabaseExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseExceptionHandler.class);

    /**
     * 处理约束违反异常 (数据库约束)
     * @param exception
     * @return
     */
    @ExceptionHandler
    public HttpRes handle(ConstraintViolationException exception) {
        logger.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", exception,exception);
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            builder.append(violation.getMessage());
            break;
        }
        return HttpResUtil.fail(builder.toString(), ErrorEnum.CONSTRAINT_EXCEPTION);
    }

}
