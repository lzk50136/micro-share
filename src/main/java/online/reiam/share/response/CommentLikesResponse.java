package online.reiam.share.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommentLikesResponse implements Serializable {

    private static final long serialVersionUID = -5304804242546325692L;

    /**
     * 评论id
     */
    private Integer commentId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 昵称
     */
    private String name;

    /**
     * 性别
     */
    private Boolean gender;

    /**
     * 头像图片
     */
    private String profilePhoto;

}
