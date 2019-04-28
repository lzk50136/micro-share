package online.reiam.share.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserFollowerResponse implements Serializable {

    private static final long serialVersionUID = -8022194517343884283L;

    /**
     * 关注者id
     */
    private Integer userId;

    /**
     * 是否互粉
     */
    private Boolean eachOther;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Boolean gender;

    /**
     * 头像图片
     */
    private String profilePhoto;

    /**
     * 网站
     */
    private String website;

    /**
     * 个人简介
     */
    private String bio;

}
