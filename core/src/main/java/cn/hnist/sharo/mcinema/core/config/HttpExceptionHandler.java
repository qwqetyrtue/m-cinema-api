package cn.hnist.sharo.mcinema.core.config;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
@Order(1)
public class HttpExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ValidationExceptionHandler.class);
    /**
     * 文件上传异常
     * @param e MultipartException
     * @return HttpRes
     */
    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public HttpRes handleMultipartException(MultipartException e) {
        logger.error("Exception,exception: %s", e);
        if(e instanceof MaxUploadSizeExceededException)
            return HttpResUtil.fail("文件大小超出限制", ErrorEnum.OVERSIZE_UPLOAD);
        else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
    }

    /**
     * 请求体缺失异常
     * @param e HttpMessageNotReadableException
     * @return HttpRes
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public HttpRes handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        logger.error("HttpMessageNotReadableException: %s",e);
        return HttpResUtil.fail(ErrorEnum.MISSING_REQUEST_BODY);
    }

    /**
     * 请求参数缺失异常
     * @param e HttpMessageNotReadableException
     * @return HttpRes
     */
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public HttpRes handleHttpMissingServletRequestParameterException(MissingServletRequestParameterException e){
        logger.error("HttpMessageNotReadableException: %s",e);
        return HttpResUtil.fail(ErrorEnum.MISSING_REQUEST_PARAMS);
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpRes handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        logger.error("HttpRequestMethodNotSupportedException: %s",e);
        return HttpResUtil.fail(ErrorEnum.METHOD_NOT_ALLOWED);
    }
}
