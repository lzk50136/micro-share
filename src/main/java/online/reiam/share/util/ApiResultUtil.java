package online.reiam.share.util;

public class ApiResultUtil {

    private ApiResultUtil() {
    }

    public static <T> ApiResult<T> success(T t) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(10000);
        apiResult.setMessage("成功");
        apiResult.setData(t);
        return apiResult;
    }

    public static <T> ApiResult<T> error(T t) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(10001);
        apiResult.setMessage("失败");
        apiResult.setData(t);
        return apiResult;
    }

    public static <T> ApiResult<T> error(int code, T t) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(code);
        apiResult.setMessage("失败");
        apiResult.setData(t);
        return apiResult;
    }

    public static <T> ApiResult<T> error(int code, String message, T t) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(code);
        apiResult.setMessage(message);
        apiResult.setData(t);
        return apiResult;
    }

}
