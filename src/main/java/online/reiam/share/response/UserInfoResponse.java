package online.reiam.share.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfoResponse implements Serializable {

    private static final long serialVersionUID = 852139331679962257L;

    /**
     * 自增id
     */
    private Integer id;

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

    /**
     * 我关注的
     */
    private Integer following;

    /**
     * 关注我的
     */
    private Integer follower;

}
