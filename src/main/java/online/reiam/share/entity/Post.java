package online.reiam.share.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 贴子表
 * </p>
 *
 * @author Lzk
 * @since 2019-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedTime;


}
