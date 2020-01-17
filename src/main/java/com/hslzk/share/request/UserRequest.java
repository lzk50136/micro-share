package com.hslzk.share.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserRequest implements Serializable {

    private static final long serialVersionUID = -6011258891185384149L;

    /**
     * 邮件发送校验
     */
    public interface Validate {
    }

    /**
     * 账号注册校验
     */
    public interface SignUp {
    }

    /**
     * 登录校验
     */
    public interface Login {
    }

    /**
     * 重置密码校验
     */
    public interface ResetPassword {
    }

    /**
     * 更新密码校验
     */
    public interface UpdatePassword {
    }

    /**
     * 用户名
     */
    @Email(message = "邮件格式错误。", groups = {Validate.class, SignUp.class, Login.class, ResetPassword.class})
    @NotBlank(message = "username不能为空。", groups = {Validate.class, SignUp.class, Login.class, ResetPassword.class})
    @JsonProperty(value = "username")
    private String username;

    /**
     * 验证码
     */
    @Max(value = 999999, message = "只能为6位数的数字", groups = {SignUp.class, ResetPassword.class})
    @Min(value = 100000, message = "只能为6位数的数字", groups = {SignUp.class, ResetPassword.class})
    @NotBlank(message = "验证码不能为空。", groups = {SignUp.class, ResetPassword.class})
    @JsonProperty(value = "code")
    private String code;

    /**
     * 密码
     */
    @Size(min = 6, max = 18, message = "密码长度6-18位。", groups = {SignUp.class, Login.class, ResetPassword.class, UpdatePassword.class})
    @NotBlank(message = "密码不能为空。", groups = {SignUp.class, Login.class, ResetPassword.class, UpdatePassword.class})
    @JsonProperty(value = "password")
    private String password;

    /**
     * 新密码
     */
    @Size(min = 6, max = 18, message = "新密码长度6-18位。", groups = UpdatePassword.class)
    @NotBlank(message = "新密码不能为空。", groups = UpdatePassword.class)
    @JsonProperty(value = "new_password")
    private String newPassword;

}
