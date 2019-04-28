package online.reiam.share.exception;

import online.reiam.share.util.ApiResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static online.reiam.share.constants.Constants.APPLICATION_JSON;


@RestController
public class NotFoundController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error", produces = APPLICATION_JSON)
    public ApiResult error() {
        throw new MicroShareException(10011, "URL不存在！");
    }

}
