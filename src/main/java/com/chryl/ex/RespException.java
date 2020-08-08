package com.chryl.ex;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Created by Chr.yl on 2020/8/8.
 *
 * @author Chr.yl
 */
@RestControllerAdvice
@Slf4j
public class RespException {

    /**
     * spring默认上传大小10MB 超出大小捕获异常MaxUploadSizeExceededException
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        return "文件大小超出10MB限制, 请压缩或降低文件质量! ";
    }
}
