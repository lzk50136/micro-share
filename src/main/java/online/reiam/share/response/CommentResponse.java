package online.reiam.share.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import online.reiam.share.entity.UserInfo;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommentResponse implements Serializable {

    private static final long serialVersionUID = -5304804242546325692L;

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 类型对应id
     */
    private Integer typeId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 回复者
     */
    private UserInfo sender;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论点赞数
     */
    private Integer likesNum;

    /**
     * 评论回复数
     */
    private Integer replyNum;

    /**
     * 回复给谁
     */
    private Integer replyTo;

    /**
     * 被回复者
     */
    private UserInfo receiver;

    /**
     * 评论类型
     */
    private Integer commentType;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;

}
