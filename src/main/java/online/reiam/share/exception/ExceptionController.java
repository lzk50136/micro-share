package online.reiam.share.exception;

import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@ControllerAdvice
@RestController
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(MicroShareException.class)
    public ApiResult microShareException(MicroShareException e) {
        return ApiResultUtil.error(e.getCode(), e.getMessage());
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