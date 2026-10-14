package com.hims.exception;

import com.hims.utils.ResultVo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultVo<String> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return ResultVo.error(e.getMessage());
    }

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVo<String> handleBusinessException(BusinessException e) {
        return ResultVo.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultVo<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResultVo.error("参数错误：" + e.getMessage());
    }

    /**
     * 处理文件上传大小超限异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResultVo<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResultVo.error("文件大小超出限制，请上传小于20MB的文件");
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ResultVo<String> handleNullPointerException(NullPointerException e) {
        e.printStackTrace();
        return ResultVo.error("系统内部错误，请稍后重试");
    }

    /**
     * 处理所有未捕获的异常
     */
    @ExceptionHandler(Exception.class)
    public ResultVo<String> handleException(Exception e) {
        e.printStackTrace();
        return ResultVo.error("系统异常：" + e.getMessage());
    }
}
