package online.reiam.share.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PostResponse implements Serializable {

    private static final long serialVersionUID = 5277054733289824475L;

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 点赞数
     */
    private Integer likesNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 允许评论
     */
    private Boolean allowComment;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;

    /**
     * 用户信息
     */
    private UserInfoResponse userInfoResponse;

    /**
     * 贴子详情
     */
    private List<PostDetailResponse> postDetailResponseList;

}
