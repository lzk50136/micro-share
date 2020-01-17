package com.hslzk.share.exception;

import com.hslzk.share.util.ApiResult;
import com.hslzk.share.util.ApiResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@ControllerAdvice
@RestController
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(MicroShareException.class)
    public ApiResult microShareException(MicroShareException e) {
        return ApiResultUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : fieldErrorList) {
            stringBuilder.append(fieldError.getDefaultMessage());
        }
        return ApiResultUtil.error(10027, stringBuilder.toString());
    }

    @ExceptionHandler(NullPointerException.class)
    public ApiResult nullPointerException(NullPointerException e) {
        logger.error(e.getMessage());
        logger.error(Arrays.toString(e.getStackTrace()));
        return ApiResultUtil.error(10002, "空指针异常！");
    }

    @ExceptionHandler(Exception.class)
    public ApiResult exception(Exception e) {
        logger.error(e.getMessage());
        logger.error(Arrays.toString(e.getStackTrace()));
        return ApiResultUtil.error(10099, "未知错误。", e.getMessage());
    }

}