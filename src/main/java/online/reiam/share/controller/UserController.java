package online.reiam.share.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.reiam.share.component.AsyncTask;
import online.reiam.share.entity.User;
import online.reiam.share.exception.MicroShareException;
import online.reiam.share.jwt.JwtTokenUtil;
import online.reiam.share.request.UserRequest;
import online.reiam.share.service.UserService;
import online.reiam.share.util.ApiResult;
import online.reiam.share.util.ApiResultUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static online.reiam.share.constants.Constants.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AsyncTask asyncTask;
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 发送注册验证码邮件
     */
    @PostMapping(value = "/sign_up_validate", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult signUpValidate(@RequestBody @Validated(UserRequest.Validate.class) UserRequest userRequest) {
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, userRequest.getUsername()));
        if (user != null) {
            throw new MicroShareException(10006, "用户名已被占用。");
        }
        String validateCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        stringRedisTemplate.opsForValue().set(userRequest.getUsername() + REDIS_REGISTER, validateCode, 10, TimeUnit.MINUTES);
        asyncTask.sendMail(userRequest.getUsername(), "欢迎注册微分享！", "您的注册验证码为：" + validateCode + "。");
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 注册
     */
    @PostMapping(value = "/sign_up", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult signUp(@RequestBody @Validated(UserRequest.SignUp.class) UserRequest userRequest) {
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, userRequest.getUsername()));
        if (user != null) {
            throw new MicroShareException(10006, "用户名已被注册。");
        }
        userService.validateCode(userRequest.getCode(), stringRedisTemplate.opsForValue().get(userRequest.getUsername() + REDIS_REGISTER));
        stringRedisTemplate.delete(userRequest.getUsername() + REDIS_REGISTER);
        userService.signUp(userRequest.getUsername(), BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()), "user");
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 登录
     */
    @PostMapping(value = "/login", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult login(@RequestBody @Validated(UserRequest.Login.class) UserRequest userRequest) {
        User user = userService.exist(userRequest.getUsername());
        if (!BCrypt.checkpw(userRequest.getPassword(), user.getPassword())) {
            throw new MicroShareException(10009, "密码错误。");
        }
        if (user.getDisabled()) {
            throw new MicroShareException(10010, "用户处于封停状态。");
        }
        String token = JwtTokenUtil.createToken(user.getId());
        stringRedisTemplate.opsForValue().set(user.getId() + REDIS_TOKEN, token, JwtTokenUtil.EXPIRATION, TimeUnit.MILLISECONDS);
        return ApiResultUtil.success(token);
    }

    /**
     * 发送重置密码邮件
     */
    @PostMapping(value = "/reset_password_validate", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult resetPasswordValidate(@RequestBody @Validated(UserRequest.Validate.class) UserRequest userRequest) {
        User user = userService.exist(userRequest.getUsername());
        String validateCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        stringRedisTemplate.opsForValue().set(userRequest.getUsername() + REDIS_RESET, validateCode, 10, TimeUnit.MINUTES);
        asyncTask.sendMail(user.getUsername(), "微分享重置密码。", "您的重置验证码为：" + validateCode + "。");
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 重置密码
     */
    @PostMapping(value = "/reset_password", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult resetPassword(@RequestBody @Validated(UserRequest.ResetPassword.class) UserRequest userRequest) {
        User user = userService.exist(userRequest.getUsername());
        if (user.getDisabled()) {
            throw new MicroShareException(10010, "用户处于封停状态。");
        }
        userService.validateCode(userRequest.getCode(), stringRedisTemplate.opsForValue().get(userRequest.getUsername() + REDIS_RESET));
        stringRedisTemplate.delete(userRequest.getUsername() + REDIS_RESET);
        User user2 = new User();
        user2.setId(user.getId())
                .setPassword(BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()))
                .setVersion(user.getVersion())
                .setModifiedTime(LocalDateTime.now());
        if (!userService.updateById(user2)) {
            throw new MicroShareException(10001, "操作失败。");
        }
        stringRedisTemplate.delete(user.getId() + REDIS_TOKEN);
        return ApiResultUtil.success("重置成功。");
    }

    /**
     * 注销
     */
    @RequiresRoles("user")
    @PostMapping(value = "/logout", produces = APPLICATION_JSON)
    public ApiResult logout(@RequestHeader("Authorization") String authorization) {
        stringRedisTemplate.delete(JwtTokenUtil.getUserId(authorization) + REDIS_TOKEN);
        return ApiResultUtil.success("操作成功。");
    }

    /**
     * 修改密码
     */
    @RequiresRoles("user")
    @PostMapping(value = "/update_password", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ApiResult updatePassword(@RequestBody @Validated(UserRequest.UpdatePassword.class) UserRequest userRequest, @RequestHeader("Authorization") String authorization) {
        User user = userService.getById(JwtTokenUtil.getUserId(authorization));
        if (!BCrypt.checkpw(userRequest.getPassword(), user.getPassword())) {
            throw new MicroShareException(10012, "密码校验错误。");
        }
        if (userRequest.getPassword().equals(userRequest.getNewPassword())) {
            throw new MicroShareException(10013, "新旧密码不能相同。");
        }
        User user2 = new User();
        user2.setId(user.getId())
                .setPassword(BCrypt.hashpw(userRequest.getNewPassword(), BCrypt.gensalt()))
                .setVersion(user.getVersion())
                .setModifiedTime(LocalDateTime.now());
        if (!userService.updateById(user2)) {
            throw new MicroShareException(10001, "操作失败。");
        }
        stringRedisTemplate.delete(user.getId() + REDIS_TOKEN);
        return ApiResultUtil.success("修改成功。");
    }

    /**
     * 刷新令牌
     */
    @RequiresRoles("user")
    @PostMapping(value = "/refresh_token", produces = APPLICATION_JSON)
    public ApiResult refreshToken(@RequestHeader("Authorization") String authorization) {
        Integer userId = JwtTokenUtil.getUserId(authorization);
        String token = JwtTokenUtil.createToken(userId);
        stringRedisTemplate.opsForValue().set(userId + REDIS_TOKEN, token, JwtTokenUtil.EXPIRATION, TimeUnit.MILLISECONDS);
        return ApiResultUtil.success(token);
    }

}
