package online.reiam.share.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfoRequest implements Serializable {

    private static final long serialVersionUID = 4340399826478578645L;

    /**
     * 获取用户信息校验
     */
    public interface GetUserInfo {
    }

    @NotNull(message = "nickname不能为空。", groups = GetUserInfo.class)
    @JsonProperty(value = "nickname")
    private String nickname;

    @JsonProperty(value = "gender")
    private Boolean gender;

    @JsonProperty(value = "website")
    private String website;

    @JsonProperty(value = "bio")
    private String bio;

}
